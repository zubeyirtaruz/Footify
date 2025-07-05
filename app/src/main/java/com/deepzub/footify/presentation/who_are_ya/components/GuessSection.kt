package com.deepzub.footify.presentation.who_are_ya.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.deepzub.footify.domain.model.Footballer

@Composable
fun GuessSection(
    userQuery: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean,
    suggestions: List<Footballer>,
    onGuessMade: (Footballer) -> Unit
) {
    GuessInputField(
        query = userQuery,
        onQueryChange = onQueryChange,
        placeholderText = placeholder,
        enabled = enabled
    )

    if (enabled && userQuery.length >= 2) {
        val filtered = suggestions
            .filter { it.name.contains(userQuery, ignoreCase = true) }
            .distinctBy { it.id }

        if (filtered.isNotEmpty()) {
            LazyColumn {
                items(filtered) { footballer ->
                    FootballerItem(footballer = footballer) {
                        onGuessMade(footballer)
                    }
                }
            }
        }
    }
}