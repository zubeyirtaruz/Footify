package com.deepzub.footify.presentation.who_are_ya

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun WhoAreYaScreen(
    navController: NavController,
    playerId: Int,
    viewModel: WhoAreYaViewModel = hiltViewModel()
) {
    val footballer by viewModel.footballer.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFootballer(playerId)
    }

    footballer?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Name: ${it.name}")
            Text(text = "Age: ${it.age}")
            Text(text = "Nationality: ${it.nationality}")
            Text(text = "Position: ${it.position}")
            AsyncImage(
                model = it.photo,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading...")
        }
    }
}