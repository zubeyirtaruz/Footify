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

@Composable
fun Keyboard(
    onKey: (Char) -> Unit,
    onDelete: () -> Unit,
    onEnter: () -> Unit
) {
    val rows = listOf(
        "QWERTYUIOP",
        "ASDFGHJKL"
    )
    // İlk iki satır orijinal gibi
    rows.forEach { row ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            row.forEach { c ->
                Key(c.toString(), modifier = Modifier.weight(1f)) { onKey(c) }
            }
        }
        Spacer(Modifier.height(6.dp))
    }

    // Alt satır: ZXC... + Delete + Enter tuşları
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Key("Delete", modifier = Modifier.weight(1.5f)) { onDelete() }
        Spacer(modifier = Modifier.width(1.dp))
        Key("Z", modifier = Modifier.weight(1f)) { onKey('Z') }
        Key("X", modifier = Modifier.weight(1f)) { onKey('X') }
        Key("C", modifier = Modifier.weight(1f)) { onKey('C') }
        Key("V", modifier = Modifier.weight(1f)) { onKey('V') }
        Key("B", modifier = Modifier.weight(1f)) { onKey('B') }
        Key("N", modifier = Modifier.weight(1f)) { onKey('N') }
        Key("M", modifier = Modifier.weight(1f)) { onKey('M') }
        Spacer(modifier = Modifier.width(1.dp))
        Key("Enter", modifier = Modifier.weight(1.5f)) { onEnter() }
    }
}