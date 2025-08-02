package com.deepzub.footify.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Shader
import android.renderscript.*
import androidx.core.graphics.createBitmap

/* ------------------------- PlayerImage ------------------------- */
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

/* ------------------------- ClubImage ------------------------- */
fun applyCircularMaskBlur(
    context: Context,
    source: Bitmap,
    guessCount: Int
): Bitmap {
    val radiusValues = listOf(6, 10, 14, 18, 22, 26)
    val revealRadius = radiusValues.getOrNull(guessCount - 1) ?: (source.width / 2)

    val grayscaleBitmap = toGrayscale(source)

    val blurred = blurBitmap(context, grayscaleBitmap, 25)

    val output = createBitmap(source.width, source.height)
    val canvas = Canvas(output)

    canvas.drawBitmap(blurred, 0f, 0f, null)

    val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    val paint = Paint().apply {
        isAntiAlias = true
        this.shader = shader
    }

    val centerX = source.width / 2f
    val centerY = source.height / 2f

    canvas.drawCircle(centerX, centerY, revealRadius.toFloat(), paint)

    return output
}

fun toGrayscale(bitmap: Bitmap): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val grayscaleBitmap = createBitmap(width, height)

    val canvas = Canvas(grayscaleBitmap)
    val paint = Paint()

    val colorMatrix = ColorMatrix()
    colorMatrix.setSaturation(0f)

    val filter = ColorMatrixColorFilter(colorMatrix)
    paint.colorFilter = filter

    canvas.drawBitmap(bitmap, 0f, 0f, paint)

    return grayscaleBitmap
}