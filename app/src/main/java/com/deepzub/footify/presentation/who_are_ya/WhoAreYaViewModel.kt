package com.deepzub.footify.presentation.who_are_ya

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepzub.footify.domain.model.Country
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.use_case.get_country.GetCountriesUseCase
import com.deepzub.footify.presentation.who_are_ya.model.GuessAttribute
import com.deepzub.footify.presentation.who_are_ya.model.GuessRow
import com.deepzub.footify.domain.use_case.get_player.GetFootballersUseCase
import com.deepzub.footify.presentation.who_are_ya.components.getPositionShortName
import com.deepzub.footify.util.Constants
import com.deepzub.footify.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WhoAreYaViewModel @Inject constructor(
    private val getFootballersUseCase: GetFootballersUseCase,
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private val _footballerState = MutableStateFlow(FootballerState())
    val footballerState: StateFlow<FootballerState> = _footballerState

    private val _countryState = MutableStateFlow(CountryState())
    val countryState: StateFlow<CountryState> = _countryState

    private val _currentPlayer = MutableStateFlow<Footballer?>(null)
    val currentPlayer: StateFlow<Footballer?> = _currentPlayer

    init {
        loadTop5Leagues()
        loadCountries()
    }

    // Tek bir lig için futbolcuları getir ve mevcut listeye ekle
    private fun loadFootballers(league: Int, season: Int) {
        getFootballersUseCase(league, season).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    // Gelen verileri filtrele (aynı ID varsa alma)
                    val newPlayers = result.data?.filter { new ->
                        _footballerState.value.footballers.none { it.id == new.id }
                    }.orEmpty()

                    // Mevcut listeye sadece yeni gelenleri ekle
                    val currentList = _footballerState.value.footballers + newPlayers

                    _footballerState.value = _footballerState.value.copy(
                        footballers = currentList,
                        isLoading = false,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    _footballerState.value = _footballerState.value.copy(
                        error = result.message ?: "Error",
                        isLoading = false
                    )
                    println("Errors: ${result.message}")
                }

                is Resource.Loading -> {
                    _footballerState.value = _footballerState.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadCountries() {
        getCountriesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _countryState.value = _countryState.value.copy(
                        countries = result.data ?: emptyList(),
                        isLoading = false,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    _countryState.value = _countryState.value.copy(
                        error = result.message ?: "Error",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _countryState.value = _countryState.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    // 5 TOP Ligten getir
    private fun loadTop5Leagues() {
        val leagues = listOf(
            Constants.PREMIER_LEAGUE_ID,
            Constants.BUNDESLIGA_ID,
            Constants.LA_LIGA_ID,
            Constants.LIGUE_1_ID,
            Constants.SERIE_A_ID
        )

        leagues.forEach { leagueId ->
            loadFootballers(league = leagueId, season = Constants.SEASON_ID)
        }
    }

    fun searchFootballers(query: String): List<Footballer> {
        return _footballerState.value.footballers.filter { footballer ->
            footballer.name.contains(query, ignoreCase = true)
        }
    }

    fun pickRandomPlayer() {
        _currentPlayer.value = _footballerState.value.footballers.randomOrNull()
    }

    fun resetGame() {
        _footballerState.update { current ->
            current.copy(guesses = emptyList())
        }
        pickRandomPlayer()
    }

    fun makeGuess(guess: Footballer) {
        val target = _currentPlayer.value ?: return

        val attrs = listOf(
            getFlagUrl(_countryState.value.countries, guess.nationality)?.let {
                GuessAttribute(
                    label = "NAT",
                    value = it,
                    isCorrect = guess.nationality == target.nationality,
                    isImage = true
                )
            },
            GuessAttribute(
                label = "LEA",
                value = guess.leagueLogo,
                isCorrect = guess.leagueLogo == target.leagueLogo,
                isImage = true
            ),
            GuessAttribute(
                label = "TEAM",
                value = guess.teamLogo,
                isCorrect = guess.teamLogo == target.teamLogo,
                isImage = true
            ),
            GuessAttribute(
                label = "POS",
                value = getPositionShortName(guess.position),
                isCorrect = getPositionShortName(guess.position) == getPositionShortName(target.position)
            ),
            GuessAttribute(
                label = "AGE",
                value = guess.age.toString(),
                isCorrect = guess.age == target.age,
                correctValue = target.age.toString()
            ),
//            GuessAttribute(
//                label = "SHIRT",
//                value =  guess.shirtNumber,
//                isCorrect = guess.shirtNumber == target.shirtNumber,
//            )
        )

        val newRow = GuessRow(guess, attrs)
        _footballerState.value = _footballerState.value.copy(
            guesses = _footballerState.value.guesses + newRow
        )
    }

    private fun getFlagUrl(countries: List<Country>, nationality: String): String? {
        return countries.find {
            it.name.trim().equals(nationality.trim(), ignoreCase = true)
        }?.flag
    }

}
