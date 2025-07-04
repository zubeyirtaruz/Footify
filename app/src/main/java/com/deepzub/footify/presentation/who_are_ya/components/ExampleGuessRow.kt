package com.deepzub.footify.presentation.who_are_ya.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepzub.footify.presentation.who_are_ya.model.AttributeType
import com.deepzub.footify.presentation.who_are_ya.model.GuessAttribute

@Composable
fun ExampleGuessRow() {
    val sampleAttrs = listOf(
        GuessAttribute(type = AttributeType.NATIONALITY, "https://media.api-sports.io/flags/pt.svg", isCorrect = true, isImage = true),
        GuessAttribute(type = AttributeType.LEAGUE, "https://media.api-sports.io/football/leagues/39.png", isCorrect = false, isImage = true),
        GuessAttribute(type = AttributeType.TEAM, "https://media.api-sports.io/football/teams/33.png", isCorrect = false, isImage = true),
        GuessAttribute(type = AttributeType.POSITION, "MF", isCorrect = true),
        GuessAttribute(type = AttributeType.AGE, "30↓", isCorrect = false),
        GuessAttribute(type = AttributeType.SHIRT, "#8↑", isCorrect = false)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        sampleAttrs.forEach { AttrBox(it) }
    }
}
