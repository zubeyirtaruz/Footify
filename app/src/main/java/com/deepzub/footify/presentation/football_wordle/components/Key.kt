package com.deepzub.footify.presentation.football_wordle.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepzub.footify.presentation.football_wordle.model.LetterStatus

@Composable
fun Key(
    label: String,
    status: LetterStatus = LetterStatus.EMPTY,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val bgColor = when (status) {
        LetterStatus.CORRECT  -> Color(0xFF00C853) // Green
        LetterStatus.PRESENT  -> Color(0xFFFFC107) // Yellow
        LetterStatus.ABSENT   -> Color(0xFF424242) // Dark gray
        LetterStatus.EMPTY    -> Color(0xFF486386) // Default key color
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues(0.dp) // Padding kaldırıldı, harf ortası daha net olur
    ) {
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}