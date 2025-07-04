package com.deepzub.footify.presentation.who_are_ya.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.deepzub.footify.presentation.who_are_ya.WhoAreYaViewModel

@Composable
fun GuessSection(
    userQuery: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean,
    viewModel: WhoAreYaViewModel,
    onGuessMade: () -> Unit
) {
    GuessInputField(
        query = userQuery,
        onQueryChange = onQueryChange,
        placeholderText = placeholder,
        enabled = enabled
    )

    if (enabled && userQuery.length >= 2) {
        val filtered = viewModel
            .searchFootballers(userQuery)
            .distinctBy { it.id }

        if (filtered.isNotEmpty()) {
            LazyColumn {
                items(filtered) { player ->
                    FootballerItem(player) {
                        viewModel.makeGuess(player)
                        onGuessMade()
                    }
                }
            }
        }
    }
}