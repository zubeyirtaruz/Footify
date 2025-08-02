package com.deepzub.footify.presentation.guess_football_club.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepzub.footify.presentation.guess_football_club.model.ClubAttributeType
import com.deepzub.footify.presentation.guess_football_club.model.ClubGuessAttribute

@Composable
fun ExampleClubGuessRow() {
    val sampleAttrs = listOf(
        ClubGuessAttribute(type = ClubAttributeType.NATION, "https://media.api-sports.io/flags/de.svg", isCorrect = true, isImage = true),
        ClubGuessAttribute(type = ClubAttributeType.EST, "1900↑", isCorrect = false),
        ClubGuessAttribute(type = ClubAttributeType.CAPACITY, "75K↓", isCorrect = false)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        sampleAttrs.forEach { ClubAttrBox(it) }
    }
}