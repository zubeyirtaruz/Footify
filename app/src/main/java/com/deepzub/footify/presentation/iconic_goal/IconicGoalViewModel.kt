package com.deepzub.footify.presentation.iconic_goal

import androidx.lifecycle.ViewModel
import com.deepzub.footify.R
import com.deepzub.footify.presentation.iconic_goal.model.Goal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class IconicGoalViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(IconicGoalState())
    val state: StateFlow<IconicGoalState> = _state

    private val goals = listOf(
        Goal(1, R.raw.cristiano_ronaldo_goal, "Cristiano Ronaldo"),
        Goal(2, R.raw.wayne_rooney_goal, "Wayne Rooney"),
    )

    init {
        loadNewGoal()
    }

    fun onEvent(event: IconicGoalEvent) {
        when (event) {
            is IconicGoalEvent.ResetGame -> loadNewGoal()

            is IconicGoalEvent.MakeGuess -> {
                val correct = event.guess.equals(_state.value.goal?.scorer, ignoreCase = true)
                _state.update {
                    it.copy(
                        userGuess = "",
                        isCorrect = correct,
                        isGameOver = true
                    )
                }
            }

            is IconicGoalEvent.OnQueryChange -> {
                _state.update { it.copy(userGuess = event.query) }
            }

            is IconicGoalEvent.ToggleHelp -> {
                _state.update { it.copy(showHelp = event.show) }
            }
        }
    }

    private fun loadNewGoal() {
        val newGoal = goals.random()
        println("New goal loaded: ${newGoal.scorer}")
        _state.value = IconicGoalState(goal = newGoal)
    }
}