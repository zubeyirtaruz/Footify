package com.deepzub.footify.presentation.career_path_challenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepzub.footify.presentation.career_path_challenge.components.ClubHistoryTable
import com.deepzub.footify.presentation.career_path_challenge.components.GuessInputField
import com.deepzub.footify.presentation.career_path_challenge.components.HelpDialog
import com.deepzub.footify.presentation.career_path_challenge.components.TopBar

@Composable
fun CareerPathScreen(
    navController: NavController,
    viewModel: CareerPathViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

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

        ClubHistoryTable(clubs = state.revealedClubs)

        Spacer(Modifier.height(24.dp))

        if (!state.isGameOver) {
            GuessInputField(
                query = state.userGuess,
                onQueryChange = { viewModel.onEvent(CareerPathEvent.OnQueryChange(it)) },
                placeholderText = "Guess ${state.currentGuess} of ${state.maxGuesses}",
                onGuessSubmit = {
                    viewModel.onEvent(CareerPathEvent.MakeGuess(it))
                }
            )
        } else {
            Text(
                text = if (state.isCorrect) "Correct! 🎉" else "Wrong! It was: ${state.footballer?.name}",
                fontWeight = FontWeight.Bold,
                color = if (state.isCorrect) Color.Green else Color.Red,
                fontSize = 18.sp
            )
        }

        if (state.showHelp) {
            HelpDialog(onDismiss = { viewModel.onEvent(CareerPathEvent.ToggleHelp(false)) })
        }
    }

}