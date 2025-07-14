package com.deepzub.footify.presentation.career_path_challenge

import com.deepzub.footify.presentation.career_path_challenge.model.ClubEntry
import com.deepzub.footify.presentation.career_path_challenge.model.Footballer

data class CareerPathState(
    val footballer: Footballer? = null,
    val revealedClubs: List<ClubEntry> = emptyList(),
    val userGuess: String = "",
    val currentGuess: Int = 1,
    val isCorrect: Boolean = false,
    val isGameOver: Boolean = false,
    val showHelp: Boolean = false,
    val maxGuesses: Int = 8
)