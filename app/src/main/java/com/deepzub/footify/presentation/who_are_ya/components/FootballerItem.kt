package com.deepzub.footify.presentation.who_are_ya.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.deepzub.footify.R
import com.deepzub.footify.domain.model.Footballer

@Composable
fun FootballerItem(footballer: Footballer, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.card_light_bg)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = footballer.teamLogo,
                contentDescription = footballer.teamName,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = footballer.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.text_primary_dark)
                )
                Text(
                    text = footballer.teamName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            PositionTag(footballer.position)
        }
    }
}

@Composable
private fun PositionTag(position: String) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(colorResource(id = R.color.attr_neutral_bg))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = getPositionShortName(position),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.text_secondary_dark)
        )
    }
}

fun getPositionShortName(position: String): String = when (position.lowercase()) {
    "goalkeeper" -> "GK"
    "defender" -> "DF"
    "midfielder" -> "MF"
    "attacker" -> "FW"
    else -> position.take(2).uppercase()
}