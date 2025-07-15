package com.deepzub.footify.presentation.career_path_challenge

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepzub.footify.presentation.career_path_challenge.components.ClubHistoryTable
import com.deepzub.footify.presentation.career_path_challenge.components.GuessInputField
import com.deepzub.footify.presentation.career_path_challenge.components.HelpDialog
import com.deepzub.footify.presentation.career_path_challenge.components.TopBar
import com.deepzub.footify.presentation.career_path_challenge.model.ClubEntry
import com.deepzub.footify.presentation.career_path_challenge.components.GuessSection
import com.deepzub.footify.util.ShowToast

@Composable
fun CareerPathScreen(
    navController: NavController,
    viewModel: CareerPathViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val displayedClubs = List(state.maxGuesses) { index ->
        if (index < state.currentGuess || state.isGameOver) {
            state.footballer?.careerPath?.getOrNull(index) ?: ClubEntry("????", "????", "??", "(?)")
        } else {
            ClubEntry("????", "????", "??", "(?)")
        }
    }

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
                    .padding(16.dp)
            ) {
                Spacer(Modifier.height(50.dp))

                TopBar(
                    onResetClick = { viewModel.onEvent(CareerPathEvent.ResetGame) },
                    onHelpClick = { viewModel.onEvent(CareerPathEvent.ToggleHelp(true)) }
                )

                Spacer(Modifier.height(16.dp))

                ClubHistoryTable(clubs = displayedClubs)

                Spacer(Modifier.height(24.dp))

                if (!state.isGameOver) {
                    var userQuery by remember { mutableStateOf("") }

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GuessInputField(
                                query = userQuery,
                                onQueryChange = {
                                    userQuery = it
                                },
                                placeholderText = "Guess ${state.currentGuess} of ${state.maxGuesses}"
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    viewModel.onEvent(CareerPathEvent.MakeGuess(userQuery))
                                    userQuery = ""
                                },
                                modifier = Modifier
                                    .height(56.dp)
                                    .defaultMinSize(minWidth = 80.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "SKIP",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }

                        GuessSection(
                            userQuery = userQuery,
                            enabled = !state.isGameOver,
                            suggestions = state.footballers,
                            onGuessMade = { guess ->
                                userQuery = ""
                                viewModel.onEvent(CareerPathEvent.MakeGuess(guess.name))
                            }
                        )
                    }
                }
                else {
                    Toast.makeText(
                        LocalContext.current,
                        "Answer: ${state.footballer?.name ?: "Unknown"}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                if (state.showHelp) {
                    HelpDialog(onDismiss = { viewModel.onEvent(CareerPathEvent.ToggleHelp(false)) })
                }
            }
        }
    }
}