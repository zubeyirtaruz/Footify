package com.deepzub.footify.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.deepzub.footify.presentation.football_games.FootballGamesScreen
import com.deepzub.footify.presentation.ui.theme.FootifyTheme
import com.deepzub.footify.presentation.who_are_ya.WhoAreYaScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FootifyTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.FootballGamesScreen.route) {

                    composable(route = Screen.FootballGamesScreen.route) {
                        FootballGamesScreen(navController = navController)
                    }

                    composable(
                        route = Screen.GameDetailScreen.route,
                        arguments = listOf(navArgument("gameId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val gameId = backStackEntry.arguments?.getInt("gameId")
                        when(gameId){
                            1 -> WhoAreYaScreen(navController = navController)
//                            2 -> Pack11Screen(navController = navController)
                            else -> {
                                println("GameId error")
                            }
                        }

                    }
                }
            }
        }
    }
}

