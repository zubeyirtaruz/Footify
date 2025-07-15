package com.deepzub.footify.presentation.career_path_challenge

sealed class CareerPathEvent {
    data class MakeGuess(val guess: String) : CareerPathEvent()
    data class ToggleHelp(val show: Boolean) : CareerPathEvent()
    object ResetGame : CareerPathEvent()
}