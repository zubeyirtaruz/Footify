package com.deepzub.footify.presentation.guess_football_club.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ClubGuessInputField(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholderText: String,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = placeholderText,
                color = Color.Gray
            )
        },
        singleLine = true,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
    )
}