package com.deepzub.footify.presentation.who_are_ya

import androidx.benchmark.perfetto.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavController
import coil.compose.AsyncImage

//@Composable
//fun WhoAreYaScreen(
//    navController: NavController,
//    playerId: Int,
//    viewModel: WhoAreYaViewModel = hiltViewModel()
//) {
//    val footballer by viewModel.footballer.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.loadFootballer(playerId)
//    }
//
//
//}

//


@Composable
fun WhoAreYaScreen(
    navController: NavController,
    viewModel: FootballerViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadFootballers(league = 39, season = 2023)
    }

    if (state.isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else if (state.error.isNotEmpty()) {
        Text(text = state.error, color = Color.Red, modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn {
            items(state.footballers) { player ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = player.photo,
                            contentDescription = player.name,
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = player.name, fontWeight = FontWeight.Bold)
                            Text(text = "Team: ${player.teamName}")
                            Text(text = "Position: ${player.position}")
                            Text(text = "Shirt: ${player.shirtNumber ?: "N/A"}")
                        }
                    }
                }
            }
        }
    }
}

