package com.kappdev.notes.feature_notes.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.kappdev.notes.feature_notes.domain.repository.StorageRepository

class StorageRepositoryImpl: StorageRepository {

    private val storageReference = FirebaseStorage.getInstance().reference

    override fun getImages(): List<Bitmap> {
        storageReference.child("backgrounds/").listAll().apply {
            addOnSuccessListener { list ->
                list.items.forEach {
                    Log.d("FireBaseStorage", "$it")
                    Log.d("FireBaseStorage", "download url: ${it.downloadUrl}")
                }
            }
            addOnFailureListener {
                Log.d("FireBaseStorage", "Something went wrong! (no data)")
            }
        }
        return emptyList()
    }

}