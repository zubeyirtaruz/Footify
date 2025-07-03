package com.deepzub.footify.presentation.who_are_ya

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.domain.use_case.get_player.GetFootballersUseCase
import com.deepzub.footify.util.Constants
import com.deepzub.footify.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WhoAreYaViewModel @Inject constructor(
    private val getFootballersUseCase: GetFootballersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FootballerState())
    val state: StateFlow<FootballerState> = _state

    private val _currentPlayer = MutableStateFlow<Footballer?>(null)
    val currentPlayer: StateFlow<Footballer?> = _currentPlayer

    init {
        loadTop5Leagues()
    }

    // Tek bir lig için futbolcuları getir ve mevcut listeye ekle
    private fun loadFootballers(league: Int, season: Int) {
        getFootballersUseCase(league, season).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    // Gelen verileri filtrele (aynı ID varsa alma)
                    val newPlayers = result.data?.filter { new ->
                        _state.value.footballers.none { it.id == new.id }
                    }.orEmpty()

                    // Mevcut listeye sadece yeni gelenleri ekle
                    val currentList = _state.value.footballers + newPlayers

                    _state.value = _state.value.copy(
                        footballers = currentList,
                        isLoading = false,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Error",
                        isLoading = false
                    )
                    println("Errors: ${result.message}")
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    // 5 TOP Ligten getir
    private fun loadTop5Leagues() {
        val leagues = listOf(
            Constants.PREMIER_LEAGUE_ID,
            Constants.BUNDESLIGA_ID,
//            Constants.LA_LIGA_ID,
//            Constants.LIGUE_1_ID,
//            Constants.SERIE_A_ID
        )

        leagues.forEach { leagueId ->
            loadFootballers(league = leagueId, season = Constants.SEASON_ID)
        }
    }

    fun searchFootballers(query: String): List<Footballer> {
        return _state.value.footballers.filter { footballer ->
            footballer.name.contains(query, ignoreCase = true)
        }
    }

    fun pickRandomPlayer() {
        _currentPlayer.value = _state.value.footballers.randomOrNull()
    }

}
