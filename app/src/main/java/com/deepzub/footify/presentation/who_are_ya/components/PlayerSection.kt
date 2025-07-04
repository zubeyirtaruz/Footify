package com.deepzub.footify.presentation.who_are_ya.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deepzub.footify.domain.model.Footballer

@Composable
fun PlayerSection(
    photoVisible: Boolean?,
    isGameOver: Boolean,
    guessCount: Int,
    currentPlayer: Footballer?
) {
    when (photoVisible) {
        null, false -> {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                contentDescription = "Mystery Player",
                tint = Color.Gray,
                modifier = Modifier.size(100.dp)
            )
        }

        true -> {
            currentPlayer?.photo?.takeIf { it.isNotBlank() }?.let { url ->
                Spacer(Modifier.height(16.dp))
                val blurGuess = if (isGameOver) 9 else guessCount
                PlayerImage(photoUrl = url, guessCount = blurGuess)
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    if (isGameOver && currentPlayer != null) {
        Text(
            text = currentPlayer.name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}