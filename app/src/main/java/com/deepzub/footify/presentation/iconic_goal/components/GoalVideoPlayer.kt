package com.deepzub.footify.presentation.iconic_goal.components

import android.net.Uri
import android.view.ViewGroup
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun GoalVideoPlayer(videoResId: Int) {
    val context = LocalContext.current
    val videoUri = Uri.parse("android.resource://${context.packageName}/$videoResId")

    val videoViewRef = remember { mutableStateOf<VideoView?>(null) }

    // videoResId değiştiğinde VideoView'e yeni URI ver
    LaunchedEffect(videoResId) {
        videoViewRef.value?.apply {
            setVideoURI(videoUri)
            setOnPreparedListener { mp ->
                mp.isLooping = true
                start()
            }
        }
    }

    AndroidView(
        factory = { ctx ->
            VideoView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    600
                )
                setVideoURI(videoUri)
                setOnPreparedListener { mp ->
                    mp.isLooping = true
                    start()
                }

                videoViewRef.value = this // referansı dışarı aktar
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

