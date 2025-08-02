package com.deepzub.footify.presentation.guess_football_club

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepzub.footify.domain.model.Club
import com.deepzub.footify.domain.model.Country
import com.deepzub.footify.domain.use_case.get_clubs.GetClubsUseCase
import com.deepzub.footify.domain.use_case.get_country.GetCountriesUseCase
import com.deepzub.footify.presentation.guess_football_club.model.ClubAttributeType
import com.deepzub.footify.presentation.guess_football_club.model.ClubGuessAttribute
import com.deepzub.footify.presentation.guess_football_club.model.ClubGuessRow
import com.deepzub.footify.util.Constants
import com.deepzub.footify.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@HiltViewModel
class GuessClubViewModel @Inject constructor(
    private val getClubs: GetClubsUseCase,
    private val getCountries: GetCountriesUseCase
) : ViewModel() {

    /* ------------------------- STATE ------------------------- */
    private val _ui = MutableStateFlow(GuessClubUiState())
    val ui: StateFlow<GuessClubUiState> = _ui

    /* ------------------------- INIT -------------------------- */
    init { onEvent(GuessClubEvent.InitData) }

    /* ------------------------- EVENTS ------------------------ */
    fun onEvent(event: GuessClubEvent) = when (event) {
        GuessClubEvent.InitData          -> loadData()
        GuessClubEvent.ResetGame         -> reset()
        is GuessClubEvent.TogglePhoto    -> _ui.update { it.copy(photoVisible = event.visible) }
        is GuessClubEvent.MakeGuess      -> handleGuess(event.club)
    }

    /* ---------------------- LOAD DATA ------------------------ */
    private fun loadData() = viewModelScope.launch {
        _ui.update { it.copy(isLoading = true, error = null) }

        // Ülkeler Flow
        val countryFlow = getCountries()
            .onEach { res ->
                when (res) {
                    is Resource.Loading -> _ui.update { it.copy(isLoading = true) }
                    is Resource.Error   -> _ui.update { it.copy(error = res.message, isLoading = false) }
                    is Resource.Success -> _ui.update { it.copy(countries = res.data ?: emptyList()) }
                }
            }

        // 5 lig kulüpleri paralel Flow
        val leagues = listOf(
            Constants.PREMIER_LEAGUE_ID,
            Constants.BUNDESLIGA_ID,
            Constants.LA_LIGA_ID,
            Constants.LIGUE_1_ID,
            Constants.SERIE_A_ID
        )

        val clubFlows = leagues.map { league ->
            getClubs(league, Constants.SEASON_ID)
        }

        // Combine tüm kulüp Flow’ları
        merge(*clubFlows.toTypedArray())
            .onEach { res ->
                when (res) {
                    is Resource.Error   -> _ui.update { it.copy(error = res.message, isLoading = false) }
                    is Resource.Loading -> _ui.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        val merged = (_ui.value.clubs + res.data.orEmpty())
                            .distinctBy { it.id }
                        _ui.update { it.copy(clubs = merged) }
                    }
                }
            }
            .launchIn(viewModelScope)

        /* Ülke Flow’u başlat (ayrı) */
        countryFlow.launchIn(viewModelScope)

        /* Flow’lardan ilk veriler gelir gelmez rastgele kulüp seç */
        viewModelScope.launch {
            ui.collect { state ->
                if (state.clubs.isNotEmpty() && state.countries.isNotEmpty()) {
                    _ui.update { it.copy(isLoading = false) }
                    pickRandomClub()
                    cancel()
                }
            }
        }
    }

    /** Rastgele bir kulüp seçer */
    private fun pickRandomClub() {
        val random = _ui.value.clubs.randomOrNull()
        _ui.update { it.copy(club = random) }
        println("New Club: ${ui.value.club?.name}")
    }

    /* ---------------------- RESET GAME ----------------------- */
    private fun reset() = _ui.update {
        it.copy(
            guesses      = emptyList(),
            guessCount   = 1,
            isGameOver   = false,
            photoVisible = null
        )
    }.also {
        pickRandomClub()
    }

    /* ---------------------- MAKE GUESS ----------------------- */
    private fun handleGuess(guess: Club) {
        val state = _ui.value
        val target = state.club ?: return
        applyGuess(guess, target, state.countries)
    }

    private fun applyGuess(guess: Club, target: Club, countries: List<Country>) {
        val attrs = buildClubAttributes(guess, target, countries)
        val newRow = ClubGuessRow(guess, attrs)
        val newCount = _ui.value.guessCount + 1
        val gameOver = attrs.all { it?.isCorrect == true } || newCount > 8

        _ui.update {
            it.copy(
                guesses = it.guesses + newRow,
                guessCount = newCount,
                isGameOver = gameOver,
                photoVisible = if (gameOver) true else it.photoVisible
            )
        }
    }

    /* -------------------- HELPERS ---------------------------- */
    private fun buildClubAttributes(
        guess: Club,
        target: Club,
        countries: List<Country>
    ): List<ClubGuessAttribute?> {
        val guessLat = guess.latitude
        val guessLon = guess.longitude
        val targetLat = target.latitude
        val targetLon = target.longitude

        val distance = if (guessLat != null && guessLon != null && targetLat != null && targetLon != null) {
            haversine(guessLat, guessLon, targetLat, targetLon)
        } else null

        val direction = if (guessLat != null && guessLon != null && targetLat != null && targetLon != null) {
            getDirection(guessLat, guessLon, targetLat, targetLon)
        } else null

        val isExact = distance != null && distance.toInt() == 0

        return listOf(
            // Nation (bayrak)
            countries.find { it.name.equals(guess.country, true) }?.flag?.let { flag ->
                ClubGuessAttribute(
                    type = ClubAttributeType.NATION,
                    value = flag,
                    isCorrect = guess.country == target.country,
                    isImage = true
                )
            },
            // Founded year
            ClubGuessAttribute(
                type = ClubAttributeType.EST,
                value = guess.founded.toString(),
                isCorrect = guess.founded == target.founded,
                correctValue = target.founded.toString()
            ),
            // Stadium capacity
            ClubGuessAttribute(
                type = ClubAttributeType.CAPACITY,
                value = guess.stadiumCapacity.toString(),
                isCorrect = guess.stadiumCapacity == target.stadiumCapacity,
                correctValue = target.stadiumCapacity.toString()
            ),
            // Direction
            direction?.let {
                ClubGuessAttribute(
                    type = ClubAttributeType.DIR,
                    value = it,
                    isCorrect = isExact, // Yön bilgisini sadece gösterim olarak kullanacağız
                    correctValue = null
                )
            },
            // Distance (km)
            distance?.let {
                ClubGuessAttribute(
                    type = ClubAttributeType.DIST,
                    value = "${it.toInt()}",
                    isCorrect = isExact,
                    correctValue = "0"
                )
            },
        )
    }

    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371 // Dünya yarıçapı km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2).pow(2.0) + Math.cos(Math.toRadians(lat1)) *
                Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2).pow(2.0)
        val c = 2 * Math.atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }

    private fun getDirection(lat1: Double, lon1: Double, lat2: Double, lon2: Double): String {
        val dLon = Math.toRadians(lon2 - lon1)
        val y = sin(dLon) * cos(Math.toRadians(lat2))
        val x = cos(Math.toRadians(lat1)) * sin(Math.toRadians(lat2)) -
                sin(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * cos(dLon)
        val bearing = (Math.toDegrees(atan2(y, x)) + 360) % 360

        return when {
            bearing in 22.5..67.5 -> "↗"
            bearing in 67.5..112.5 -> "→"
            bearing in 112.5..157.5 -> "↘"
            bearing in 157.5..202.5 -> "↓"
            bearing in 202.5..247.5 -> "↙"
            bearing in 247.5..292.5 -> "←"
            bearing in 292.5..337.5 -> "↖"
            else -> "↑"
        }
    }

}
