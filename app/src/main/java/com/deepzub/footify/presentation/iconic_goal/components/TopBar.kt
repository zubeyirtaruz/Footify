package com.deepzub.footify.presentation.iconic_goal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    onResetClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onResetClick) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "New Goal",
                tint = Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }

        IconButton(onClick = onHelpClick) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "How to Play",
                tint = Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}