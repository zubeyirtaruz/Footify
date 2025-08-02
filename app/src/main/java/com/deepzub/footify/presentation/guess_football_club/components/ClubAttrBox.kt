package com.deepzub.footify.presentation.guess_football_club.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.East
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material.icons.filled.South
import androidx.compose.material.icons.filled.SouthEast
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material.icons.filled.West
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.deepzub.footify.R
import com.deepzub.footify.presentation.guess_football_club.model.ClubAttributeType
import com.deepzub.footify.presentation.guess_football_club.model.ClubGuessAttribute

@Composable
fun ClubAttrBox(attr: ClubGuessAttribute, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bgColor = if (attr.isCorrect)
            colorResource(id = R.color.attr_correct_bg)
        else
            colorResource(id = R.color.attr_neutral_bg)

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            when {
                attr.isImage -> {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(attr.value)
                            .decoderFactory(SvgDecoder.Factory())
                            .crossfade(true)
                            .build(),
                        contentDescription = attr.label,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                attr.type == ClubAttributeType.DIR -> {
                    val (icon, label) = when (attr.value) {
                        "↗" -> Icons.Default.NorthEast to "NE"
                        "→" -> Icons.Default.East to "E"
                        "↘" -> Icons.Default.SouthEast to "SE"
                        "↓" -> Icons.Default.South to "S"
                        "↙" -> Icons.Default.SouthWest to "SW"
                        "←" -> Icons.Default.West to "W"
                        "↖" -> Icons.Default.NorthWest to "NW"
                        else -> Icons.Default.North to "N"
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = if (attr.isCorrect) Icons.Default.LocationOn else icon,
                            contentDescription = attr.label,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        if (!attr.isCorrect) {
                            Text(
                                text = label,
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                else -> {
                    Text(
                        text = getDisplayAttrValue(attr),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = attr.label,
            fontSize = 10.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
    }
}

fun getDisplayAttrValue(attr: ClubGuessAttribute): String {
    val arrowTypes = setOf(ClubAttributeType.EST, ClubAttributeType.CAPACITY)
    val guessed = attr.value.toIntOrNull()
    val actual  = attr.correctValue?.toIntOrNull()

    return if (attr.type in arrowTypes && guessed != null && actual != null) {
        when {
            guessed < actual -> "$guessed↑"
            guessed > actual -> "$guessed↓"
            else             -> guessed.toString()
        }
    } else attr.value
}