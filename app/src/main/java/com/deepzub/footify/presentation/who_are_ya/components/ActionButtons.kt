package com.deepzub.footify.presentation.who_are_ya.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtons(
    onHideClick: () -> Unit,
    onShowClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(
            onClick = onHideClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF635BFF))
        ) {
            Icon(Icons.Default.VisibilityOff, contentDescription = "Hide")
            Spacer(Modifier.width(8.dp))
            Text("Hide Photo")
        }

        Button(
            onClick = onShowClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF635BFF))
        ) {
            Icon(Icons.Default.Visibility, contentDescription = "Show")
            Spacer(Modifier.width(8.dp))
            Text("Show Photo")
        }
    }
}