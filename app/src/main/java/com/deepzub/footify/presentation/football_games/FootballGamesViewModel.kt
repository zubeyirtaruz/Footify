package com.deepzub.footify.presentation.football_games

import androidx.lifecycle.ViewModel
import com.deepzub.footify.R
import com.deepzub.footify.presentation.football_games.model.GameItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FootballGamesViewModel : ViewModel() {

    private val _games = MutableStateFlow(emptyList<GameItem>())
    val games: StateFlow<List<GameItem>> = _games

    init {
        loadGames()
    }

    private fun loadGames() {
        _games.value = listOf(
            GameItem(id = 1, name = "Who Are Ya?", imageRes = R.drawable.who_are_ya),
            GameItem(id = 2, name = "Football Wordle", imageRes = R.drawable.wordle),
            GameItem(id = 3, name = "Iconic Goal", imageRes = R.drawable.connections),
            GameItem(id = 4, name = "Career Path Challenge", imageRes = R.drawable.career_path),
            GameItem(id = 5, name = "Guess the Football Club", imageRes = R.drawable.guess_club),
            GameItem(id = 6, name = "Football Bingo", upComing = true, imageRes = R.drawable.bingo),
            GameItem(id = 7, name = "SuperDraft Soccer", upComing = true, imageRes = R.drawable.superdraft),
            GameItem(id = 8, name = "Pack 11", upComing = true, imageRes = R.drawable.pack11),
            GameItem(id = 9, name = "BOX2BOX", upComing = true, imageRes = R.drawable.box2box)
        )
    }
}