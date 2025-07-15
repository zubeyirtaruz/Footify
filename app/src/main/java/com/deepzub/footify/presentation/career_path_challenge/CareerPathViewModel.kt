package com.deepzub.footify.presentation.career_path_challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepzub.footify.domain.use_case.get_footballers_for_career_path.GetFootballersForCareerPath
import com.deepzub.footify.presentation.career_path_challenge.model.ClubEntry
import com.deepzub.footify.presentation.career_path_challenge.model.Footballer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.deepzub.footify.util.Constants
import com.deepzub.footify.util.Resource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CareerPathViewModel @Inject constructor(
    private val getFootballers: GetFootballersForCareerPath
) : ViewModel() {

    private val _state = MutableStateFlow(CareerPathState())
    val state: StateFlow<CareerPathState> = _state

    init {
        loadData()
    }

    fun onEvent(event: CareerPathEvent) {
        when (event) {
            is CareerPathEvent.MakeGuess -> {
                val current = _state.value
                val footballer = current.footballer ?: return
                val correct = event.guess.equals(footballer.name, ignoreCase = true)

                val newRevealedCount = when {
                    correct -> footballer.careerPath.size
                    current.revealedCount < footballer.careerPath.size -> current.revealedCount + 1
                    else -> current.revealedCount
                }

                _state.update {
                    it.copy(
                        isCorrect = correct,
                        currentGuess = current.currentGuess + 1,
                        revealedCount = newRevealedCount,
                        isGameOver = correct || newRevealedCount >= footballer.careerPath.size
                    )
                }
            }

            is CareerPathEvent.ToggleHelp -> {
                _state.update { it.copy(showHelp = event.show) }
            }

            is CareerPathEvent.ResetGame -> {
            }
        }
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        // 5 lig futbolcuları paralel Flow
        val leagues = listOf(
            Constants.PREMIER_LEAGUE_ID,
//            Constants.BUNDESLIGA_ID,
//            Constants.LA_LIGA_ID,
//            Constants.LIGUE_1_ID,
//            Constants.SERIE_A_ID
        )

        val footballerFlows = leagues.map { league ->
            getFootballers(league, Constants.SEASON_ID)
        }

        // Combine tüm futbolcu Flow’ları
        merge(*footballerFlows.toTypedArray())
            .onEach { res ->
                when (res) {
                    is Resource.Error   -> _state.update { it.copy(error = res.message, isLoading = false) }
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        val merged = (_state.value.footballers + res.data.orEmpty())
                            .distinctBy { it.id }
                        _state.update { it.copy(footballers = merged) }
                    }
                }
            }
            .launchIn(viewModelScope)

        /* Flow’lardan ilk veriler gelir gelmez rastgele oyuncu seç */
        viewModelScope.launch {
            state.collect { state ->
                if (state.footballers.isNotEmpty() ) {
                    _state.update { it.copy(isLoading = false) }
                    pickRandomPlayer()
                    cancel()
                }
            }
        }

    }

    private fun pickRandomPlayer() {

        val random = _state.value.footballers.randomOrNull()
        _state.update { it.copy(player = random) }
        println("New Footballer: ${state.value.player?.name}")

        val clubs = listOf(
            ClubEntry("1993–1995", "Rosario Central", "51", "(7)"),
            ClubEntry("1995–1999", "Rosario Central", "100", "(15)"),
            ClubEntry("1999–2004", "Rosario Central", "124", "(25)"),
            ClubEntry("2004–2005", "Rosario Central", "10", "(2)"),
            ClubEntry("2005–2011", "Rosario Central", "111", "(50)"),
            ClubEntry("2011–2017", "Rosario Central", "140", "(34)"),
            ClubEntry("2017–2022", "Rosario Central", "88", "(25)"),
            ClubEntry("2022–2025", "Rosario Central", "54", "(5)")
        )

        val player = state.value.player?.name?.let { Footballer(it, clubs) }

        _state.update {
            it.copy(
                footballer = player,
                maxGuesses = clubs.size,
                isLoading = false,
                currentGuess = 1,
                isCorrect = false,
                isGameOver = false,
                revealedCount = 0
            )
        }
    }
}