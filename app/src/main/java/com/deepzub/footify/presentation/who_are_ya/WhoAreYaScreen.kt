package com.deepzub.footify.presentation.who_are_ya

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepzub.footify.util.ShowToast
import com.deepzub.footify.presentation.who_are_ya.components.FootballerItem
import com.deepzub.footify.presentation.who_are_ya.components.GuessInputField
import com.deepzub.footify.presentation.who_are_ya.components.GuessRowItem
import com.deepzub.footify.presentation.who_are_ya.components.HelpDialog
import com.deepzub.footify.presentation.who_are_ya.components.PlayerImage

@Composable
fun WhoAreYaScreen(
    navController: NavController,
    viewModel: WhoAreYaViewModel = hiltViewModel()
) {
    val footballerState by viewModel.footballerState.collectAsState()
    val countryState by viewModel.countryState.collectAsState()
    val currentPlayer by viewModel.currentPlayer.collectAsState()

    var photoVisible by remember { mutableStateOf<Boolean?>(null) }
    var guessCount by remember { mutableStateOf(1) }
    var userQuery by remember { mutableStateOf("") }
    var isGameOver by remember { mutableStateOf(false) }
    var showHelp by remember { mutableStateOf(false) }

    val isDataReady = footballerState.footballers.isNotEmpty()
            && !footballerState.isLoading
            && countryState.countries.isNotEmpty()
            && !countryState.isLoading

    LaunchedEffect(isDataReady) {
        if (isDataReady) {
            viewModel.pickRandomPlayer()
        }
        println(viewModel.currentPlayer.value?.name)
    }

    LaunchedEffect(guessCount, footballerState.guesses) {
        if (footballerState.guesses.isNotEmpty()) {
            val lastGuess = footballerState.guesses.last()
            val allCorrect = lastGuess.attributes.all { it?.isCorrect == true }

            if (allCorrect) {
                isGameOver = true          // ➜ Kazandı
                photoVisible = true        // ➜ Foto + isim göster
            } else if (guessCount > 8) {
                isGameOver = true          // ➜ Haklar bitti
                photoVisible = true        // ➜ Foto + isim göster
            }
        }
    }

    when {
        footballerState.isLoading || countryState.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        footballerState.error.isNotEmpty() -> {
            ShowToast(footballerState.error)
        }

        countryState.error.isNotEmpty() -> {
            ShowToast(countryState.error)
        }

        isDataReady -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            viewModel.resetGame()
                            photoVisible = null
                            userQuery = ""
                            guessCount = 1
                            isGameOver = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "New Footballer",
                            tint = Color.Gray,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    IconButton(onClick = { showHelp = true }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "How to Play",
                            tint = Color.Gray,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                if (photoVisible == null) {
                    Text(
                        "BIG 5",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(24.dp))
                }

                if (photoVisible == null || photoVisible == false) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = "How to Play",
                        tint = Color.Gray,
                        modifier = Modifier.size(100.dp)
                    )
                }

                if (photoVisible == true) {
                    currentPlayer?.photo?.takeIf { it.isNotBlank() }?.let { url ->
                        Spacer(Modifier.height(16.dp))
                        val blurGuess = if (isGameOver) 9 else guessCount   // 9 → calculateBlurLevel() 0 döndürsün
                        PlayerImage(photoUrl = url, guessCount = blurGuess)
                    }
                }

                Spacer(Modifier.height(24.dp))

                if (photoVisible != null) {
                    if (isGameOver && currentPlayer != null) {
                        Text(
                            text = currentPlayer!!.name,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        GuessInputField(
                            query = userQuery,
                            onQueryChange = { userQuery = it },
                            placeholderText = "GUESS $guessCount OF 8",
                            enabled = !isGameOver
                        )

                        if (userQuery.length >= 2) {
                            val filtered = viewModel.searchFootballers(userQuery)
                            LazyColumn {
                                items(filtered) { player ->
                                    FootballerItem(player) {
                                        if (!isGameOver) {
                                            viewModel.makeGuess(player)
                                            userQuery = ""
                                            guessCount += 1
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (footballerState.guesses.isNotEmpty()) {
                        Spacer(Modifier.height(16.dp))
                        LazyColumn {
                            items(footballerState.guesses) { row ->
                                GuessRowItem(row)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                if (photoVisible == null) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(
                            onClick = { photoVisible = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF635BFF))
                        ) {
                            Icon(Icons.Default.VisibilityOff, contentDescription = "Hide")
                            Spacer(Modifier.width(8.dp))
                            Text("Hide Photo")
                        }

                        Button(
                            onClick = { photoVisible = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF635BFF))
                        ) {
                            Icon(Icons.Default.Visibility, contentDescription = "Show")
                            Spacer(Modifier.width(8.dp))
                            Text("Show Photo")
                        }
                    }
                }
            }

            if (showHelp) {
                HelpDialog(onDismiss = { showHelp = false })
            }

        }
    }
}