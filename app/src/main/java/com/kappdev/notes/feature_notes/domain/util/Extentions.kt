package com.kappdev.notes.feature_notes.domain.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
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

fun Context.openAppSettings() {
    val settingsIntent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    this.startActivity(settingsIntent)
}