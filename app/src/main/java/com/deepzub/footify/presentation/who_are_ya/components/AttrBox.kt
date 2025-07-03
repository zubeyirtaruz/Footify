package com.deepzub.footify.presentation.who_are_ya.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.deepzub.footify.presentation.who_are_ya.model.GuessAttribute

@Composable
fun AttrBox(attr: GuessAttribute, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ⬇️ İçerik kutusu (text veya görsel olabilir)
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(if (attr.isCorrect) Color(0xFF4CAF50) else Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            if (attr.isImage) {
                AsyncImage(
                    model = attr.value,
                    contentDescription = attr.label,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = attr.value,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    ),
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