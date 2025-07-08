package com.deepzub.footify.presentation.football_wordle.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepzub.footify.presentation.football_wordle.model.LetterStatus
import com.deepzub.footify.presentation.football_wordle.model.TileState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Tile(tile: TileState, wordLength: Int, indexInRow: Int) {
    val size = when {
        wordLength <= 3 -> 54.dp
        wordLength <= 5 -> 48.dp
        wordLength <= 7 -> 42.dp
        wordLength <= 9 -> 36.dp
        else            -> 32.dp
    }

    val targetColor = when (tile.status) {
        LetterStatus.EMPTY   -> Color(0xFF1E1E1E)
        LetterStatus.CORRECT -> Color(0xFF00C853)
        LetterStatus.PRESENT -> Color(0xFFFFC107)
        LetterStatus.ABSENT  -> Color(0xFF424242)
    }

    val colorAnim  = remember { Animatable(Color(0xFF1E1E1E)) }
    val scaleAnim  = remember { Animatable(1f) }
    val rotateAnim = remember { Animatable(0f) }

    // Harf girilince scale animasyonu
    LaunchedEffect(tile.letter) {
        if (tile.letter != ' ') {
            scaleAnim.animateTo(1.2f, tween(100))
            scaleAnim.animateTo(1f,  tween(100))
        }
    }

    // Durum değişince sırayla dön ve renk değiştir
    LaunchedEffect(tile.status) {
        if (tile.status != LetterStatus.EMPTY) {
            delay(indexInRow * 150L)
            rotateAnim.snapTo(0f)

            // Dönüş başlasın
            launch {
                rotateAnim.animateTo(
                    180f,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
            }

            // 90 dereceye gelince rengi başlat
            delay(250L)
            colorAnim.animateTo(targetColor, tween(300))
        } else {
            rotateAnim.snapTo(0f)
            colorAnim.snapTo(targetColor)
        }
    }

    Box(
        modifier = Modifier
            .size(size)
            .graphicsLayer {
                scaleX = scaleAnim.value
                scaleY = scaleAnim.value
            },
        contentAlignment = Alignment.Center
    ) {
        // Arka plan döner, harf dönmez
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    rotationY = rotateAnim.value
                    cameraDistance = 8 * density
                }
                .clip(CircleShape)
                .background(colorAnim.value)
        )

        Text(
            text = tile.letter.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}