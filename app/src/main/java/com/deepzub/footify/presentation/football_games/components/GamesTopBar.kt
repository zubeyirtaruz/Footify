package com.deepzub.footify.presentation.football_games.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Football Games",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF2C1A4C)
            )
        }
    )
}