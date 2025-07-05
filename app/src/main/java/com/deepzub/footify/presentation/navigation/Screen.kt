package com.deepzub.footify.presentation.navigation

sealed class Screen(val route : String) {
    data object FootballGamesScreen : Screen(route = "footbal_games_screen")

    data object GameDetailScreen : Screen(route = "game_detail_screen/{gameId}") {
        fun passGameId(gameId: Int): String {
            return "game_detail_screen/$gameId"
        }
    }
}