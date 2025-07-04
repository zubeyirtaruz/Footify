package com.deepzub.footify.presentation.who_are_ya.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepzub.footify.presentation.who_are_ya.model.GuessAttribute

@Composable
fun ExampleGuessRow() {
    val sampleAttrs = listOf(
        GuessAttribute(label = "NAT", value = "https://media.api-sports.io/flags/pt.svg", isCorrect = true, isImage = true),
        GuessAttribute(label = "LEA", value = "https://media.api-sports.io/football/leagues/39.png", isCorrect = false, isImage = true),
        GuessAttribute(label = "TEAM", value = "https://media.api-sports.io/football/teams/33.png", isCorrect = false, isImage = true),
        GuessAttribute(label = "POS", value = "MF", isCorrect = true),
        GuessAttribute(label = "AGE", value = "30↓", isCorrect = false),
        GuessAttribute(label = "SHIRT", value = "#8↑", isCorrect = false)
    )

    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        sampleAttrs.forEach { AttrBox(it) }
    }
}
