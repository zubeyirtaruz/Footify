package com.deepzub.footify.presentation.career_path_challenge.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepzub.footify.presentation.career_path_challenge.model.ClubEntry

@Composable
fun ClubHistoryTable(clubs: List<ClubEntry>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFEEF2F5))
            .padding(12.dp)
    ) {
        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB0C4DE))
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Years",
                modifier = Modifier.weight(0.25f),
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = "Team",
                modifier = Modifier.weight(0.35f),
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = "Apps",
                modifier = Modifier.weight(0.2f),
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = "(Gls)",
                modifier = Modifier.weight(0.2f),
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        // Table Rows
        clubs.forEach { club ->
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = club.years,
                    modifier = Modifier.weight(0.25f),
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = club.team,
                    modifier = Modifier.weight(0.35f),
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = club.apps,
                    modifier = Modifier.weight(0.2f),
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = club.goals,
                    modifier = Modifier.weight(0.2f),
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}