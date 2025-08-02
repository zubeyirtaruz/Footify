package com.deepzub.footify.presentation.guess_football_club

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.deepzub.footify.presentation.guess_football_club.components.ClubActionButtons
import com.deepzub.footify.presentation.guess_football_club.components.ClubGuessRowItem
import com.deepzub.footify.presentation.guess_football_club.components.ClubGuessSection
import com.deepzub.footify.presentation.guess_football_club.components.ClubSection
import com.deepzub.footify.presentation.guess_football_club.components.GuessClubHeader
import com.deepzub.footify.presentation.guess_football_club.components.GuessClubTopBar
import com.deepzub.footify.presentation.guess_football_club.components.HelpDialog
import com.deepzub.footify.util.ShowToast

@Composable
fun GuessClubScreen(
    navController: NavController,
    viewModel: GuessClubViewModel = hiltViewModel()
) {
    val state by viewModel.ui.collectAsState()

    var userQuery by remember { mutableStateOf("") }
    var showHelp by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                // Top Bar
                GuessClubTopBar(
                    onResetClick = {
                        viewModel.onEvent(GuessClubEvent.ResetGame)
                        userQuery = ""
                    },
                    onHelpClick = { showHelp = true }
                )

                // Başlık
                if (state.photoVisible == null) {
                    GuessClubHeader()
                }

                // Fotoğraf veya soru işareti
                ClubSection(
                    photoVisible = state.photoVisible,
                    isGameOver = state.isGameOver,
                    guessCount = state.guessCount,
                    currentClub = state.club
                )

                // Tahmin alanı
                if (state.photoVisible != null) {
                    if (!state.isGameOver) {
                        ClubGuessSection(
                            userQuery = userQuery,
                            onQueryChange = { userQuery = it },
                            placeholder = "GUESS ${state.guessCount} OF 6",
                            enabled = !state.isGameOver,
                            suggestions = state.clubs,
                            onGuessMade = { guess ->
                                userQuery = ""
                                viewModel.onEvent(GuessClubEvent.MakeGuess(guess))
                            }
                        )
                    }

                    LaunchedEffect(state.guesses.size) {
                        listState.animateScrollToItem(0)
                    }

                    // Tahmin geçmişi
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.weight(1f)
                    ) {
                        items(
                            items = state.guesses.reversed(), // En son tahmin en üstte
                            key = { it.club.name } // benzersiz bir ID
                        ) { row ->
                            ClubGuessRowItem(row)
                        }
                    }
                }

                // Başlangıçta fotoğrafı göster/gizle butonları
                if (state.photoVisible == null) {
                    Spacer(Modifier.height(24.dp))
                    ClubActionButtons(
                        onHideClick = {
                            viewModel.onEvent(GuessClubEvent.TogglePhoto(false))
                        },
                        onShowClick = {
                            viewModel.onEvent(GuessClubEvent.TogglePhoto(true))
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
