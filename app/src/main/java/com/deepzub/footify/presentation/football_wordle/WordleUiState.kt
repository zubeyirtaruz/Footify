package com.deepzub.footify.presentation.football_wordle

import com.deepzub.footify.presentation.football_wordle.model.TileState

data class WordleUiState(
    val board: List<List<TileState>> = List(6) { List(5) { TileState() } },
    val currentRow: Int = 0,
    val currentCol: Int = 0,
    val gameOver: Boolean = false,
    val message: String? = null
)