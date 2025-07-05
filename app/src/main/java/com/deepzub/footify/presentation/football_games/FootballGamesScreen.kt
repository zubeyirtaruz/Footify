package com.deepzub.footify.presentation.football_games

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepzub.footify.presentation.navigation.Screen
import com.deepzub.footify.presentation.football_games.components.GameListItem
import com.deepzub.footify.presentation.football_games.components.GamesTopBar

@Composable
fun FootballGamesScreen(
    navController: NavController,
    viewModel: FootballGamesViewModel = hiltViewModel()
) {
    val gameList by viewModel.games.collectAsState()

    Scaffold(
        topBar = {
            GamesTopBar()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            items(gameList) { game ->
                GameListItem(game = game, onClick = {
                    navController.navigate(Screen.GameDetailScreen.passGameId(game.id))
                })
            }
        }
    }
}