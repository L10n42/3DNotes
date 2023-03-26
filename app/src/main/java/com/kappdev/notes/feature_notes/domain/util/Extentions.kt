package com.kappdev.notes.feature_notes.domain.util

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color


fun <T> SnapshotStateList<T>.swap(firstIndex: Int, secondIndex: Int) {
    val first = this[firstIndex]
    this[firstIndex] = this[secondIndex]
    this[secondIndex] = first
}

fun Color.plus(color: Color): Color {
    return Color(
        red = this.red * color.red,
        green = this.green * color.green,
        blue = this.blue * color.blue,
        alpha = this.alpha * color.alpha,
    )
}