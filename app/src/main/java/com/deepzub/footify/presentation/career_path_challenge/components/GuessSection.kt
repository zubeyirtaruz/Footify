package com.deepzub.footify.presentation.career_path_challenge.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepzub.footify.domain.model.Footballer

@Composable
fun GuessSection(
    userQuery: String,
    enabled: Boolean,
    suggestions: List<Footballer>,
    onGuessMade: (Footballer) -> Unit
) {
    if (enabled && userQuery.length >= 2) {
        val filtered = suggestions
            .filter { it.name.contains(userQuery, ignoreCase = true) }
            .distinctBy { it.id }

        if (filtered.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .heightIn(max = 300.dp) // Çok uzamasın
            ) {
                items(filtered) { footballer ->
                    FootballerItem(footballer = footballer) {
                        onGuessMade(footballer)
                    }
                }
            }
        }
    }
}