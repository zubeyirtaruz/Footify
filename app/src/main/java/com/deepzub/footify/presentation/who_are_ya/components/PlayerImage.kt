package com.deepzub.footify.presentation.who_are_ya.components

import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.BlurTransformation

@Composable
fun PlayerImage(photoUrl: String, guessCount: Int) {
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(200, 200)
                scaleType = ImageView.ScaleType.CENTER_CROP
                clipToOutline = true
            }
        },
        update = { imageView ->
            Glide.with(imageView.context)
                .load(photoUrl)
                .transform(BlurTransformation(calculateBlurLevel(guessCount)))
                .into(imageView)
        },
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
    )
}

fun calculateBlurLevel(guessCount: Int): Int {
    return when (guessCount) {
        1 -> 31
        2 -> 28
        3 -> 25
        4 -> 21
        5 -> 17
        6 -> 13
        7 -> 9
        8 -> 5
        else -> 0
    }
}