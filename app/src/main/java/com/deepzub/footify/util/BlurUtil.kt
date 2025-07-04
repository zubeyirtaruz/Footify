package com.deepzub.footify.util

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.*
import androidx.core.graphics.createBitmap

fun blurBitmap(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
    if (radius <= 0) return bitmap

    val outputBitmap = createBitmap(bitmap.width, bitmap.height)
    val renderScript = RenderScript.create(context)

    val input = Allocation.createFromBitmap(renderScript, bitmap)
    val output = Allocation.createFromBitmap(renderScript, outputBitmap)

    val script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
    script.setRadius(radius.coerceIn(0, 25).toFloat())
    script.setInput(input)
    script.forEach(output)

    output.copyTo(outputBitmap)
    renderScript.destroy()

    return outputBitmap
}

fun calculateBlurLevel(guessCount: Int): Int {
    return when (guessCount) {
        1 -> 27
        2 -> 24
        3 -> 21
        4 -> 18
        5 -> 15
        6 -> 12
        7 -> 9
        8 -> 6
        else -> 0
    }
}