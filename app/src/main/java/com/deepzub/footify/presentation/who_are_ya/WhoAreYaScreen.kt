package com.deepzub.footify.presentation.who_are_ya

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepzub.footify.presentation.who_are_ya.components.ActionButtons
import com.deepzub.footify.presentation.who_are_ya.components.Big5Header
import com.deepzub.footify.util.ShowToast
import com.deepzub.footify.presentation.who_are_ya.components.GuessRowItem
import com.deepzub.footify.presentation.who_are_ya.components.GuessSection
import com.deepzub.footify.presentation.who_are_ya.components.HelpDialog
import com.deepzub.footify.presentation.who_are_ya.components.PlayerSection
import com.deepzub.footify.presentation.who_are_ya.components.WhoAreYaTopBar

@Composable
fun WhoAreYaScreen(
    navController: NavController,
    viewModel: WhoAreYaViewModel = hiltViewModel()
) {
    val state by viewModel.ui.collectAsState()

    var userQuery by remember { mutableStateOf("") }
    var showHelp by remember { mutableStateOf(false) }

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            ShowToast(state.error ?: "Unknown error")
        }

        else -> {
            println(state.player?.name)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                // Top Bar
                WhoAreYaTopBar(
                    onResetClick = {
                        viewModel.onEvent(WhoAreYaEvent.ResetGame)
                        userQuery = ""
                    },
                    onHelpClick = { showHelp = true }
                )

                // Başlık
                if (state.photoVisible == null) {
                    Big5Header()
                }

                // Fotoğraf veya soru işareti
                PlayerSection(
                    photoVisible = state.photoVisible,
                    isGameOver = state.isGameOver,
                    guessCount = state.guessCount,
                    currentPlayer = state.player
                )

                // Tahmin alanı
                if (state.photoVisible != null) {
                    if (!state.isGameOver) {
                        GuessSection(
                            userQuery = userQuery,
                            onQueryChange = { userQuery = it },
                            placeholder = "GUESS ${state.guessCount} OF 8",
                            enabled = !state.isGameOver,
                            suggestions = state.footballers,
                            onGuessMade = { guess ->
                                userQuery = ""
                                viewModel.onEvent(WhoAreYaEvent.MakeGuess(guess))
                            }
                        )
                    }

                    // Tahmin geçmişi
                    if (state.guesses.isNotEmpty()) {
                        Spacer(Modifier.height(16.dp))
                        LazyColumn {
                            items(state.guesses) { row ->
                                GuessRowItem(row)
                            }
                        }
                    }
                }

                // Başlangıçta fotoğrafı göster/gizle butonları
                if (state.photoVisible == null) {
                    Spacer(Modifier.height(24.dp))
                    ActionButtons(
                        onHideClick = {
                            viewModel.onEvent(WhoAreYaEvent.TogglePhoto(false))
                        },
                        onShowClick = {
                            viewModel.onEvent(WhoAreYaEvent.TogglePhoto(true))
                        }
                    )
                }
            }

            // Yardım popup
            if (showHelp) {
                HelpDialog(onDismiss = { showHelp = false })
            }
        }
    }
}