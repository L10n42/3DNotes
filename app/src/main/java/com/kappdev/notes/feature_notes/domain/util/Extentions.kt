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
import kotlin.random.Random
import kotlin.random.nextInt

fun <T> SnapshotStateList<T>.swap(firstIndex: Int, secondIndex: Int) {
    val first = this[firstIndex]
    this[firstIndex] = this[secondIndex]
    this[secondIndex] = first
}

fun Context.openAppSettings() {
    val settingsIntent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    this.startActivity(settingsIntent)
}