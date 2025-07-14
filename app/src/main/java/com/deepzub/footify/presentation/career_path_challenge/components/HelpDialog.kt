package com.deepzub.footify.presentation.career_path_challenge.components

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
                Bullet("Guess the iconic footballer in the same number of tries than clubs they have played for.")
                Spacer(modifier = Modifier.height(8.dp))
                Bullet("Today the player has played for 8 clubs so you get 8 guesses.")
                Spacer(modifier = Modifier.height(8.dp))
                Bullet("The mystery footballer's first club will be shown in a Wikipedia style table before you begin.")
                Spacer(modifier = Modifier.height(8.dp))
                Bullet("After each guess, the next club will be shown.")
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