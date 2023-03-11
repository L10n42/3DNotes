package com.kappdev.notes.feature_notes.domain.repository

import android.net.Uri

interface StorageRepository {

    suspend fun getImages(onSuccess: (List<Uri>) -> Unit, onFailure: () -> Unit)
}