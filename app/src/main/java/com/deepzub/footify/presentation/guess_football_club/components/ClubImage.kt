package com.deepzub.footify.presentation.guess_football_club.components

import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.deepzub.footify.util.applyCircularMaskBlur
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ClubImage(
    photoUrl: String,
    guessCount: Int,
    modifier: Modifier = Modifier.size(200.dp)
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }

    val originalBitmap = remember(photoUrl) { mutableStateOf<Bitmap?>(null) }

    val blurredBitmap by remember(photoUrl, guessCount, originalBitmap.value) {
        derivedStateOf {
            originalBitmap.value?.let {
                applyCircularMaskBlur(context, it, guessCount)
            }
        }
    }

    LaunchedEffect(photoUrl) {
        isLoading = true
        val loadedBitmap = withContext(Dispatchers.IO) {
            try {
                Glide.with(context)
                    .asBitmap()
                    .load(photoUrl)
                    .submit()
                    .get()
            } catch (e: Exception) {
                null
            }
        }
        loadedBitmap?.let { originalBitmap.value = it }
        isLoading = false
    }

    Box(
        modifier = modifier.clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading || blurredBitmap == null) {
            CircularProgressIndicator(modifier = Modifier.size(36.dp))
        } else {
            AndroidView(
                factory = {
                    ImageView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        clipToOutline = true
                        setImageBitmap(blurredBitmap)
                    }
                },
                modifier = Modifier.matchParentSize(),
                update = { it.setImageBitmap(blurredBitmap) }
            )
        }
    }
}