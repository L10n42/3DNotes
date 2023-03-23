package com.kappdev.notes.feature_notes.domain.util

import androidx.compose.runtime.snapshots.SnapshotStateList


fun <T> SnapshotStateList<T>.swap(firstIndex: Int, secondIndex: Int) {
    val first = this[firstIndex]
    this[firstIndex] = this[secondIndex]
    this[secondIndex] = first
}