package com.kappdev.notes.feature_notes.domain.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.nextInt

fun Bitmap.majorColorFrom(
    yRange: IntRange = 0 until this.height,
    xRange: IntRange = 0 until this.width
): Color {
    val clippedBitmap = this.clip(
        Rect(xRange.first, yRange.first, xRange.last, yRange.last)
    )

    val palette = Palette.from(clippedBitmap).generate()
    val majorColor = palette.getDominantColor(android.graphics.Color.TRANSPARENT)
    return Color(majorColor)
}

fun Bitmap.clip(
    area: Rect = Rect(0, 0, this.width, this.height)
): Bitmap {
    val clippedBitmap = Bitmap.createBitmap(area.width(), area.height(), Bitmap.Config.ARGB_8888)

    val canvas = Canvas(clippedBitmap)
    val paint = Paint().apply { isAntiAlias = true }
    canvas.drawBitmap(this, area, area, paint)

    return clippedBitmap
}

fun Bitmap.getAverageOf(y: Int, step: Int = 20): Color {
    val colorsList = mutableListOf<Color>()
    var currentX = 1

    while (currentX < this.width) {
        val color = this.colorAt(currentX, y)
        colorsList.add(color)
        currentX += step
    }

    val red = (colorsList.map { it.red }.average() * 255).roundToInt()
    val green = (colorsList.map { it.green }.average() * 255).roundToInt()
    val blue = (colorsList.map { it.blue }.average() * 255).roundToInt()
    return Color(red, green, blue)
}

fun Bitmap.getAverageOf(yRange: IntRange, xStep: Int = 10): Color {
    val colorsList = mutableListOf<Color>()
    var currentX = 1

    while (currentX < this.width) {
        for (num in 1..3) {
            val color = this.colorAt(currentX, Random.nextInt(yRange))
            colorsList.add(color)
        }
        currentX += xStep
    }

    val red = (colorsList.map { it.red }.average() * 255).roundToInt()
    val green = (colorsList.map { it.green }.average() * 255).roundToInt()
    val blue = (colorsList.map { it.blue }.average() * 255).roundToInt()
    return Color(red, green, blue)
}

fun Bitmap.colorAt(x: Int, y: Int): Color {
    val pixelColor = this.getPixel(x, y)

    val alpha = android.graphics.Color.alpha(pixelColor)
    val red = android.graphics.Color.red(pixelColor)
    val green = android.graphics.Color.green(pixelColor)
    val blue = android.graphics.Color.blue(pixelColor)
    return Color(red, green, blue, alpha)
}
