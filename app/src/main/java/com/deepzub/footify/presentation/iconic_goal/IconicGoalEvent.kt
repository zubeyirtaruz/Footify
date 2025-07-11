package com.deepzub.footify.presentation.iconic_goal

sealed class IconicGoalEvent {
    data class MakeGuess(val guess: String) : IconicGoalEvent()
    data class OnQueryChange(val query: String) : IconicGoalEvent()
    data class ToggleHelp(val show: Boolean) : IconicGoalEvent()
    data object ResetGame : IconicGoalEvent()
}