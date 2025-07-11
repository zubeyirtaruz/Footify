package com.deepzub.footify.presentation.iconic_goal.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun GuessInputField(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholderText: String,
    onGuessSubmit: (String) -> Unit
) {
    var text by remember { mutableStateOf(query) }

    Column {
        TextField(
            value = text,
            onValueChange = {
                text = it
                onQueryChange(it)
            },
            placeholder = { Text(placeholderText) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onGuessSubmit(text)
                text = ""
            })
        )
    }
}