package com.deepzub.footify.presentation.guess_football_club.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepzub.footify.R
import com.deepzub.footify.presentation.guess_football_club.model.ClubGuessRow
import kotlinx.coroutines.delay

@Composable
fun ClubGuessRowItem(row: ClubGuessRow) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.guess_row_bg)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = row.club.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                row.attributes.forEachIndexed { index, attr ->
                    attr?.let {
                        val rotation = remember { Animatable(0f) }

                        if (!row.animated) {
                            LaunchedEffect(Unit) {
                                delay(index * 80L)
                                rotation.animateTo(180f, tween(500))
                                row.animated = true
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .graphicsLayer {
                                    rotationY = rotation.value
                                    cameraDistance = 8 * density
                                }
                        ) {
                            Box(
                                modifier = Modifier.graphicsLayer {
                                    rotationY = if (rotation.value <= 90f) 0f else 180f
                                }
                            ) {
                                ClubAttrBox(attr = it)
                            }
                        }
                    }
                }
            }
        }
    }
}