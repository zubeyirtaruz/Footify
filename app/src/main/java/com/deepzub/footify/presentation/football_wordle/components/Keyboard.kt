package com.deepzub.footify.presentation.football_wordle.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepzub.footify.presentation.football_wordle.model.LetterStatus

@Composable
fun Keyboard(
    letterStat: Map<Char, LetterStatus>,
    onKey: (Char) -> Unit,
    onDelete: () -> Unit,
    onEnter: () -> Unit
) {
    val rows = listOf(
        "QWERTYUIOP",
        "ASDFGHJKL"
    )

    // İlk iki satır
    rows.forEach { row ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            row.forEach { c ->
                val stat = letterStat[c] ?: LetterStatus.EMPTY
                Key(label = c.toString(), status = stat, modifier = Modifier.weight(1f)) {
                    onKey(c)
                }
            }
        }
        Spacer(Modifier.height(6.dp))
    }

    // Üçüncü satır (alt sıra)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Key("Delete", modifier = Modifier.weight(1.5f)) { onDelete() }
        Spacer(modifier = Modifier.width(1.dp))

        val thirdRowLetters = listOf('Z', 'X', 'C', 'V', 'B', 'N', 'M')
        thirdRowLetters.forEach { c ->
            val stat = letterStat[c] ?: LetterStatus.EMPTY
            Key(label = c.toString(), status = stat, modifier = Modifier.weight(1f)) {
                onKey(c)
            }
        }

        Spacer(modifier = Modifier.width(1.dp))
        Key("Enter", modifier = Modifier.weight(1.5f)) { onEnter() }
    }
}