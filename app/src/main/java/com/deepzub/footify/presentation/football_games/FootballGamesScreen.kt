package com.deepzub.footify.presentation.football_games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deepzub.footify.domain.model.GameItem
import com.deepzub.footify.presentation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FootballGamesScreen(navController: NavController) {

    val gameList = listOf(
        GameItem(id = 1, name = "Who Are Ya?"),
        GameItem(id = 2, name = "Pack 11"),
        GameItem(id = 3, name = "Football Connections"),
        GameItem(id = 4, name = "Football Bingo"),
        GameItem(id = 5, name = "Career Path Challenge"),
        GameItem(id = 6, name = "SuperDraft Soccer",),
        GameItem(id = 7, name = "Guess the Football Club"),
        GameItem(id = 8, name = "Football Wordle"),
        GameItem(id = 9, name = "BOX2BOX")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Football Games") },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            items(gameList) { game ->
                GameListItem(game = game, navController = navController)
            }
        }

    }
}

@Composable
fun GameListItem(
    game: GameItem,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                navController.navigate(Screen.GameDetailScreen.passGameId(game.id))
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C1A4C))

    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Green, shape = CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = game.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}