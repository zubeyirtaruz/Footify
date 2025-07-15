package com.deepzub.footify.presentation.career_path_challenge

import com.deepzub.footify.presentation.career_path_challenge.model.Footballer

data class CareerPathState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val player: com.deepzub.footify.domain.model.Footballer? = null,
    val footballers: List<com.deepzub.footify.domain.model.Footballer> = emptyList(),
    val footballer: Footballer? = null,
    val revealedCount: Int = 0,
    val currentGuess: Int = 1,
    val isCorrect: Boolean = false,
    val isGameOver: Boolean = false,
    val showHelp: Boolean = false,
    val maxGuesses: Int = 8
)