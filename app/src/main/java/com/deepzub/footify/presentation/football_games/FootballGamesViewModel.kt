package com.deepzub.footify.presentation.football_games

import androidx.lifecycle.ViewModel
import com.deepzub.footify.presentation.football_games.model.GameItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FootballGamesViewModel : ViewModel() {

    private val _games = MutableStateFlow<List<GameItem>>(emptyList())
    val games: StateFlow<List<GameItem>> = _games

    init {
        loadGames()
    }

    private fun loadGames() {
        _games.value = listOf(
            GameItem(id = 1, name = "Who Are Ya?"),
            GameItem(id = 2, name = "Pack 11"),
            GameItem(id = 3, name = "Football Connections"),
            GameItem(id = 4, name = "Football Bingo"),
            GameItem(id = 5, name = "Career Path Challenge"),
            GameItem(id = 6, name = "SuperDraft Soccer"),
            GameItem(id = 7, name = "Guess the Football Club"),
            GameItem(id = 8, name = "Football Wordle"),
            GameItem(id = 9, name = "BOX2BOX")
        )
    }
}