package com.kappdev.notes.feature_notes.domain.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt

fun <T> SnapshotStateList<T>.swap(firstIndex: Int, secondIndex: Int) {
    val first = this[firstIndex]
    this[firstIndex] = this[secondIndex]
    this[secondIndex] = first
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

fun Bitmap.colorAt(x: Int, y: Int): Color {
    val pixelColor = this.getPixel(x, y)

    val alpha = android.graphics.Color.alpha(pixelColor)
    val red = android.graphics.Color.red(pixelColor)
    val green = android.graphics.Color.green(pixelColor)
    val blue = android.graphics.Color.blue(pixelColor)
    return Color(red, green, blue, alpha)
}

fun Color.overlay(color: Color, ration: Float = 0.5f): Color {
    return Color(ColorUtils.blendARGB(this.toArgb(), color.toArgb(), ration))
}

fun Color.plus(color: Color): Color {
    return Color(
        red = this.red * color.red,
        green = this.green * color.green,
        blue = this.blue * color.blue,
        alpha = this.alpha * color.alpha,
    )
}

fun Context.openAppSettings() {
    val settingsIntent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    this.startActivity(settingsIntent)
}