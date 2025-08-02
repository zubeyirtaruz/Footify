package com.deepzub.footify.presentation.guess_football_club.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.deepzub.footify.R

@Composable
fun HelpDialog(onDismiss: () -> Unit) {

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
                    "How to play?",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Spacer(Modifier.height(12.dp))

                Bullet("Test your football club knowledge and guess the football team in 8 tries. The mystery club can be located anywhere in the world.")
                Bullet("If you choose to show the club badge, there will be a small section of the team's crest visible. It will become slightly larger after each guess. For more of a challenge, selecting the hide badge option will mean that you will be entirely reliant upon the feedback to your guesses.")
                Bullet("Feedback will be revealed comparing your guessed club. For example, if you guess FC Bayern Munchen…")

                Spacer(Modifier.height(8.dp))
                ExampleClubGuessRow()
                Spacer(Modifier.height(8.dp))

                Bullet("… Means that the mystery team is based in Germany, and was founded later than 1900. The stadium capacity is lower than 75,000, in the north direction and is 351 kilometers away.")

                Spacer(Modifier.height(16.dp))
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

@Composable
fun Bullet(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {

        Text("•  ", color = colorResource(id = R.color.bullet_color))
        Text(text, color = Color.White, style = MaterialTheme.typography.bodySmall)
    }
}