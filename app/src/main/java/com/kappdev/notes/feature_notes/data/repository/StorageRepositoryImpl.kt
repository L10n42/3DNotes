package com.kappdev.notes.feature_notes.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.kappdev.notes.feature_notes.domain.repository.StorageRepository
import kotlinx.coroutines.tasks.await

class StorageRepositoryImpl: StorageRepository {

    private val storageReference = FirebaseStorage.getInstance().reference

    override suspend fun getImages(
        onSuccess: (List<Uri>) -> Unit,
        onFailure: () -> Unit
    ) {
        storageReference.child("backgrounds/").listAll().apply {
            addOnSuccessListener { resultList ->
                val list = mutableListOf<Uri>()
                resultList.items.forEachIndexed { index, storage ->
                    storage.downloadUrl.addOnSuccessListener {
                        list.add(it)
                        if (index == resultList.items.lastIndex) onSuccess(list)
                    }
                }
            }
            addOnFailureListener { onFailure() }
        }
    }

}