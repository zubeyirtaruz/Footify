package com.deepzub.footify.presentation.football_games.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deepzub.footify.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesTopBar() {
    TopAppBar(
        modifier = Modifier,
        title = {
            Text(
                text = "Football Games",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = colorResource(id = R.color.games_topbar_title)
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.SportsSoccer,
                contentDescription = "App Icon",
                tint = colorResource(id = R.color.games_topbar_title),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(28.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.games_topbar_bg),
            scrolledContainerColor = colorResource(id = R.color.games_topbar_bg)
        )
    )
}