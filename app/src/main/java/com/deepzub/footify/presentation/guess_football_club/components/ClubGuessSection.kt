package com.deepzub.footify.presentation.guess_football_club.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.deepzub.footify.domain.model.Club

@Composable
fun ClubGuessSection(
    userQuery: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean,
    suggestions: List<Club>,
    onGuessMade: (Club) -> Unit
) {
    ClubGuessInputField(
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
                items(filtered) { club ->
                    ClubItem(club = club) {
                        onGuessMade(club)
                    }
                }
            }
        }
    }
}