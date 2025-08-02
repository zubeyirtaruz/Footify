package com.deepzub.footify.presentation.guess_football_club.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
            if (attr.isImage) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(attr.value)
                        .decoderFactory(SvgDecoder.Factory())
                        .crossfade(true)
                        .build(),
                    contentDescription = attr.label,
                    modifier = Modifier.size(40.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = getDisplayAttrValue(attr),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
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