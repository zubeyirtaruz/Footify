package com.deepzub.footify.presentation.who_are_ya

import com.deepzub.footify.domain.model.Country
import com.deepzub.footify.domain.model.Footballer
import com.deepzub.footify.presentation.who_are_ya.model.GuessRow

data class WhoAreYaUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val player: Footballer? = null,
    val countries: List<Country> = emptyList(),
    val footballers: List<Footballer> = emptyList(),
    val guesses: List<GuessRow> = emptyList(),
    val photoVisible: Boolean? = null,
    val guessCount: Int = 1,
    val isGameOver: Boolean = false,
)