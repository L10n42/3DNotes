package com.kappdev.notes.feature_notes.domain.repository

import android.graphics.Bitmap

interface StorageRepository {

    fun getImages(): List<Bitmap>
}