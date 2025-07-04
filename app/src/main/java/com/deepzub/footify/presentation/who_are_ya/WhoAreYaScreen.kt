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

    // Oyuncu ilk defa yüklendiğinde
    LaunchedEffect(isDataReady) {
        if (isDataReady) {
            viewModel.pickRandomPlayer()
        }
    }

    // Doğru tahmin veya hak dolunca
    LaunchedEffect(guessCount, footballerState.guesses) {
        val lastGuess = footballerState.guesses.lastOrNull()
        val isCorrect = lastGuess?.attributes?.all { it?.isCorrect == true } ?: false

        if (isCorrect || guessCount > 8) {
            isGameOver = true
            photoVisible = true
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
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Top Bar
                WhoAreYaTopBar(
                    onResetClick = {
                        viewModel.resetGame()
                        photoVisible = null
                        userQuery = ""
                        guessCount = 1
                        isGameOver = false
                    },
                    onHelpClick = { showHelp = true }
                )

                // Başlık
                if (photoVisible == null) Big5Header()

                // Fotoğraf veya soru işareti
                PlayerSection(
                    photoVisible = photoVisible,
                    isGameOver = isGameOver,
                    guessCount = guessCount,
                    currentPlayer = currentPlayer
                )

                // Tahmin alanı
                if (photoVisible != null) {
                    if (!isGameOver) {
                        GuessSection(
                            userQuery = userQuery,
                            onQueryChange = { userQuery = it },
                            placeholder = "GUESS $guessCount OF 8",
                            enabled = !isGameOver,
                            viewModel = viewModel,
                            onGuessMade = {
                                userQuery = ""
                                guessCount += 1
                            }
                        )
                    } else {
                        // Oyun bittiğinde isim gösterimi zaten PlayerSection'da
                    }

                    // Yapılan tahminler
                    if (footballerState.guesses.isNotEmpty()) {
                        Spacer(Modifier.height(16.dp))
                        LazyColumn {
                            items(footballerState.guesses) { row ->
                                GuessRowItem(row)
                            }
                        }
                    }
                }

                // Başlangıçta fotoğrafı göster/gizle butonları
                if (photoVisible == null) {
                    Spacer(Modifier.height(24.dp))
                    ActionButtons(
                        onHideClick = { photoVisible = false },
                        onShowClick = { photoVisible = true }
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
