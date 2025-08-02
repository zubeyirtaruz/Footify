package com.deepzub.footify.presentation.guess_football_club

import com.deepzub.footify.domain.model.Club
import com.deepzub.footify.domain.model.Country
import com.deepzub.footify.presentation.guess_football_club.model.ClubGuessRow

data class GuessClubUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val club: Club? = null,
    val countries: List<Country> = emptyList(),
    val clubs: List<Club> = emptyList(),
    val guesses: List<ClubGuessRow> = emptyList(),
    val photoVisible: Boolean? = null,
    val guessCount: Int = 1,
    val isGameOver: Boolean = false,
)