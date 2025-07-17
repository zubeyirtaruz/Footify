package com.deepzub.footify.presentation.who_are_ya

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepzub.footify.domain.model.Country
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.use_case.get_country.GetCountriesUseCase
import com.deepzub.footify.presentation.who_are_ya.model.GuessAttribute
import com.deepzub.footify.presentation.who_are_ya.model.GuessRow
import com.deepzub.footify.domain.use_case.get_footballers.GetFootballersUseCase
import com.deepzub.footify.domain.use_case.get_one_player.GetOnePlayerUseCase
import com.deepzub.footify.presentation.who_are_ya.components.getPositionShortName
import com.deepzub.footify.presentation.who_are_ya.model.AttributeType
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

@HiltViewModel
class WhoAreYaViewModel @Inject constructor(
    private val getFootballers: GetFootballersUseCase,
    private val getCountries: GetCountriesUseCase,
    private val getOnePlayer: GetOnePlayerUseCase
) : ViewModel() {

    /* ------------------------- STATE ------------------------- */
    private val _ui = MutableStateFlow(WhoAreYaUiState())
    val ui: StateFlow<WhoAreYaUiState> = _ui

    /* ------------------------- INIT -------------------------- */
    init { onEvent(WhoAreYaEvent.InitData) }

    /* ------------------------- EVENTS ------------------------ */
    fun onEvent(event: WhoAreYaEvent) = when (event) {
        WhoAreYaEvent.InitData          -> loadData()
        WhoAreYaEvent.ResetGame         -> reset()
        is WhoAreYaEvent.TogglePhoto    -> _ui.update { it.copy(photoVisible = event.visible) }
        is WhoAreYaEvent.MakeGuess      -> handleGuess(event.footballer)
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

        // 5 lig futbolcuları paralel Flow
        val leagues = listOf(
            Constants.PREMIER_LEAGUE_ID,
            Constants.BUNDESLIGA_ID,
            Constants.LA_LIGA_ID,
            Constants.LIGUE_1_ID,
            Constants.SERIE_A_ID
        )

        val footballerFlows = leagues.map { league ->
            getFootballers(league, Constants.SEASON_ID)
        }

        // Combine tüm futbolcu Flow’ları
        merge(*footballerFlows.toTypedArray())
            .onEach { res ->
                when (res) {
                    is Resource.Error   -> _ui.update { it.copy(error = res.message, isLoading = false) }
                    is Resource.Loading -> _ui.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        val merged = (_ui.value.footballers + res.data.orEmpty())
                            .distinctBy { it.id }
                        _ui.update { it.copy(footballers = merged) }
                    }
                }
            }
            .launchIn(viewModelScope)

        /* Ülke Flow’u başlat (ayrı) */
        countryFlow.launchIn(viewModelScope)

        /* Flow’lardan ilk veriler gelir gelmez rastgele oyuncu seç */
        viewModelScope.launch {
            ui.collect { state ->
                if (state.footballers.isNotEmpty() && state.countries.isNotEmpty()) {
                    _ui.update { it.copy(isLoading = false) }
                    pickRandomPlayer()
                    cancel()
                }
            }
        }
    }

    /** Rastgele bir oyuncu seçer ve forma numarasını API’den çeker */
    private fun pickRandomPlayer() {
        val random = _ui.value.footballers.randomOrNull()
        _ui.update { it.copy(player = random) }
        random?.id?.let { fetchShirtNumber(it) }
        println("New Footballer: ${ui.value.player?.name}")
    }

    private fun fetchShirtNumber(playerId: Int) {
        getOnePlayer(playerId).onEach { res ->
            if (res is Resource.Success && res.data?.isNotEmpty() == true) {
                val shirt = res.data.first().shirtNumber
                _ui.update { state ->
                    state.copy(
                        player = state.player?.copy(shirtNumber = shirt.toString())
                    )
                }
            }
        }.launchIn(viewModelScope)
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
        pickRandomPlayer()
    }

    /* ---------------------- MAKE GUESS ----------------------- */
    private fun handleGuess(guess: Footballer) {
        val state = _ui.value
        val target = state.player ?: return

        // Eğer guess.shirtNumber boşsa, API'den çek
        if (guess.shirtNumber.isEmpty()) {
            viewModelScope.launch {
                getOnePlayer(guess.id).collect { res ->
                    if (res is Resource.Success && res.data?.isNotEmpty() == true) {
                        val updatedGuess = guess.copy(shirtNumber = res.data.first().shirtNumber.toString())
                        applyGuess(updatedGuess, target, state.countries)
                    }
                }
            }
        } else {
            applyGuess(guess, target, state.countries)
        }
    }

    private fun applyGuess(guess: Footballer, target: Footballer, countries: List<Country>) {
        val attrs = buildAttributes(guess, target, countries)
        val newRow = GuessRow(guess, attrs)
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
    private fun buildAttributes(
        guess: Footballer,
        target: Footballer,
        countries: List<Country>
    ): List<GuessAttribute?> = listOf(

        countries.find { it.name.equals(guess.nationality, true) }?.flag?.let { flag ->
            GuessAttribute(AttributeType.NATIONALITY, flag, guess.nationality == target.nationality, isImage = true)
        },

        GuessAttribute(
            type = AttributeType.LEAGUE,
            value = guess.leagueLogo,
            isCorrect = guess.leagueLogo == target.leagueLogo,
            isImage = true
        ),
        GuessAttribute(
            type = AttributeType.TEAM,
            value =  guess.teamLogo,
            isCorrect = guess.teamLogo  == target.teamLogo,
            isImage = true
        ),
        GuessAttribute(
            type = AttributeType.POSITION,
            value = getPositionShortName(guess.position),
            isCorrect = getPositionShortName(guess.position) == getPositionShortName(target.position)
        ),
        GuessAttribute(
            type = AttributeType.AGE,
            value = guess.age.toString(),
            isCorrect = guess.age == target.age,
            correctValue = target.age.toString()
        ),
        GuessAttribute(
            type = AttributeType.SHIRT,
            value = guess.shirtNumber,
            isCorrect = guess.shirtNumber == target.shirtNumber,
            correctValue = target.shirtNumber
        )
    )
}