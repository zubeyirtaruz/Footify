package com.deepzub.footify.presentation.career_path_challenge

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.deepzub.footify.presentation.career_path_challenge.model.ClubEntry
import com.deepzub.footify.presentation.career_path_challenge.model.Footballer
import kotlinx.coroutines.flow.update

@HiltViewModel
class CareerPathViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(CareerPathState())
    val state: StateFlow<CareerPathState> = _state

    init {
        loadNewPlayer()
    }

    fun onEvent(event: CareerPathEvent) {
        when (event) {
            is CareerPathEvent.MakeGuess -> {
                val correct = event.guess.equals(_state.value.footballer?.name, ignoreCase = true)
                val newClubList = _state.value.revealedClubs.toMutableList().apply {
                    if (!correct && size < (_state.value.footballer?.careerPath?.size ?: 8)) {
                        add(_state.value.footballer?.careerPath?.getOrNull(size) ?: ClubEntry("????", "????", "??", "??"))
                    }
                }

                _state.update {
                    it.copy(
                        userGuess = "",
                        isCorrect = correct,
                        currentGuess = it.currentGuess + 1,
                        revealedClubs = newClubList,
                        isGameOver = correct || newClubList.size >= it.maxGuesses
                    )
                }
            }

            is CareerPathEvent.OnQueryChange -> {
                _state.update { it.copy(userGuess = event.query) }
            }

            is CareerPathEvent.ToggleHelp -> {
                _state.update { it.copy(showHelp = event.show) }
            }

            is CareerPathEvent.ResetGame -> {
                loadNewPlayer()
            }
        }
    }

    private fun loadNewPlayer() {
        // Dummy data
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
        val player = Footballer("Rooney", clubs)

        _state.value = CareerPathState(
            footballer = player,
            revealedClubs = listOf(player.careerPath.first())
        )
    }

}