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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.deepzub.footify.presentation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FootballGamesScreen(navController: NavController) {

    val gameList = listOf(
        GameItem(id = 1, name = "Who Are Ya?", true),
        GameItem(id = 2, name = "Pack 11", true),
        GameItem(id = 3, name = "Football Connections", true),
        GameItem(id = 4, name = "Football Bingo", true, isMultiplayer = true),
        GameItem(id = 5, name = "Career Path Challenge", true),
        GameItem(id = 6, name = "SuperDraft Soccer", true),
        GameItem(id = 7, name = "Guess the Football Club", true),
        GameItem(id = 8, name = "Football Wordle", true),
        GameItem(id = 9, name = "BOX2BOX", true),
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
            // Basit bir ikon simgesi (Dilerseniz Image ile özelleştirebilirsiniz)
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

                Row {
                    if (game.isDaily) {
                        LabelChip(text = "Daily", color = Color(0xFF756EF3))
                    }
                    if (game.isNew) {
                        Spacer(modifier = Modifier.width(4.dp))
                        LabelChip(text = "New", color = Color(0xFFE14D4D))
                    }
                    if (game.isMultiplayer) {
                        Spacer(modifier = Modifier.width(4.dp))
                        LabelChip(text = "Multiplayer", color = Color(0xFFE7E44F), textColor = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun LabelChip(
    text: String,
    color: Color,
    textColor: Color = Color.White
) {
    Box(
        modifier = Modifier
            .background(color, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

data class GameItem(
    val id: Int,
    val name: String,
    val isDaily: Boolean = false,
    val isNew: Boolean = false,
    val isMultiplayer: Boolean = false
)
