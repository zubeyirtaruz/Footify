package com.deepzub.footify.presentation.who_are_ya.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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

                Bullet("You have 8 chances to guess the football player from the Premier League, Bundesliga, LaLiga, Serie A or Ligue 1.")
                Bullet("Choose 'Show Photo' to see a blurred picture of the mystery footballer to get started or 'Hide Photo' and make a random opening guess.")
                Bullet("After each guess, feedback will be revealed, showing how close your guess is to the mystery footballer in a number of categories.")
                Bullet("Example guess: BRUNO FERNANDES")

                Spacer(Modifier.height(8.dp))
                ExampleGuessRow()
                Spacer(Modifier.height(8.dp))

                Bullet("… Means that the mystery player is Portuguese, does not play in the Premier League and does not play for Manchester United. However, the player is a midfielder and they are under the age of 30 whilst having a higher shirt number than 8.")
                Bullet("You can also change the player by clicking refresh icon on the top left.")

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Got it")
                }
            }
        }
    }
}