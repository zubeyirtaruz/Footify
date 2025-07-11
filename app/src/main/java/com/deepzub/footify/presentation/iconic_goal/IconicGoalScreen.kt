package com.deepzub.footify.presentation.iconic_goal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepzub.footify.presentation.iconic_goal.components.GoalVideoPlayer
import com.deepzub.footify.presentation.iconic_goal.components.GuessInputField
import com.deepzub.footify.presentation.iconic_goal.components.HelpDialog
import com.deepzub.footify.presentation.iconic_goal.components.TopBar

@Composable
fun IconicGoalScreen(
    navController: NavController,
    viewModel: IconicGoalViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            // Gerekirse burada ekstra player.release() tetikleyebilirsin
            // örn. ViewModel'de bir player varsa vs.
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(50.dp))


        // Top Bar
        TopBar(
            onResetClick = { viewModel.onEvent(IconicGoalEvent.ResetGame) },
            onHelpClick = { viewModel.onEvent(IconicGoalEvent.ToggleHelp(true)) }
        )

        Spacer(Modifier.height(16.dp))

        // Video Player
        state.goal?.let {
            GoalVideoPlayer(videoResId = it.videoResId)
        }

        Spacer(Modifier.height(24.dp))

        // Guess Input
        if (!state.isGameOver) {
            GuessInputField(
                query = state.userGuess,
                onQueryChange = { viewModel.onEvent(IconicGoalEvent.OnQueryChange(it)) },
                placeholderText = "Who scored this goal?",
                onGuessSubmit = {
                    viewModel.onEvent(IconicGoalEvent.MakeGuess(it))
                }
            )
        } else {
            Text(
                text = if (state.isCorrect) "Correct! 🎉" else "Wrong! It was: ${state.goal?.scorer}",
                fontWeight = FontWeight.Bold,
                color = if (state.isCorrect) Color.Green else Color.Red,
                fontSize = 18.sp
            )
        }

        if (state.showHelp) {
            HelpDialog(onDismiss = { viewModel.onEvent(IconicGoalEvent.ToggleHelp(false)) })
        }
    }
}