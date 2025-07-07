package com.deepzub.footify.presentation.football_wordle.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.deepzub.footify.R

@Composable
fun FootballWordleHelpDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            color = colorResource(id = R.color.help_dialog_bg)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "How to play?",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Guess present footballer in 6 tries. After each guess and just like Wordle, the colour of the tiles will change to show how close your guess was to the player.",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(24.dp))

                ExampleWordRow(
                    word = "MESSI",
                    coloredIndex = 0,
                    color = Color(0xFF00C853), // Green
                    description = "The letter M is in the player's name and in the correct spot."
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExampleWordRow(
                    word = "NEUER",
                    coloredIndex = 2,
                    color = Color(0xFFFFC107), // Yellow
                    description = "The letter U is in the player's name but in the wrong spot."
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExampleWordRow(
                    word = "VARDY",
                    coloredIndex = 3,
                    color = Color(0xFF424242), // Dark gray
                    description = "The letter D is not in the player's name in any spot."
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "After 2 attempts, the player's country will be revealed at the top of the board.",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Got it", color = Color.Black)
                }
            }
        }
    }
}