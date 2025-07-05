package com.deepzub.footify.presentation.football_games.model

data class GameItem(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val upComing: Boolean = false
)
