package com.deepzub.footify.presentation.football_wordle.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepzub.footify.presentation.football_wordle.model.LetterStatus
import com.deepzub.footify.presentation.football_wordle.model.TileState

@Composable
fun Tile(tile: TileState) {
    val bg = when (tile.status) {
        LetterStatus.EMPTY    -> Color(0xFF1E1E1E)
        LetterStatus.CORRECT  -> Color(0xFF00C853) // green
        LetterStatus.PRESENT  -> Color(0xFFFFC107) // yellow
        LetterStatus.ABSENT   -> Color(0xFF424242) // gray
    }
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = tile.letter.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}