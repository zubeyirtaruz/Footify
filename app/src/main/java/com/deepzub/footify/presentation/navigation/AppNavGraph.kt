package com.deepzub.footify.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.deepzub.footify.presentation.career_path_challenge.CareerPathScreen
import com.deepzub.footify.presentation.football_games.FootballGamesScreen
import com.deepzub.footify.presentation.football_wordle.FootballWordleScreen
import com.deepzub.footify.presentation.iconic_goal.IconicGoalScreen
import com.deepzub.footify.presentation.who_are_ya.WhoAreYaScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.FootballGamesScreen.route) {

        composable(route = Screen.FootballGamesScreen.route) {
            FootballGamesScreen(navController = navController)
        }

        composable(
            route = Screen.GameDetailScreen.route,
            arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId")
            when (gameId) {
                ScreenName.WhoAreYa.id -> WhoAreYaScreen(navController)
                ScreenName.FootballWordle.id -> FootballWordleScreen(navController)
                ScreenName.IconicGoal.id -> IconicGoalScreen(navController)
                ScreenName.CareerPathChallenge.id -> CareerPathScreen(navController)
                else -> {
                    println("Unknown gameId: $gameId")
                }
            }
        }
    }
}