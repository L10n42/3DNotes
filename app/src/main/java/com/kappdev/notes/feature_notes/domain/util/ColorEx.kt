package com.kappdev.notes.feature_notes.domain.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

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
