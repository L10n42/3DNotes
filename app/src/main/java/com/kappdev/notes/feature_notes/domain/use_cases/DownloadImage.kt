package com.kappdev.notes.feature_notes.domain.use_cases

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.kappdev.notes.R

class DownloadImage(
    private val context: Context
) {

    @Throws(DownloadImageException::class)
    suspend operator fun invoke(uri: Uri): Bitmap {
        try {
            val loading = ImageLoader(context)
            val request = ImageRequest.Builder(context).data(uri).build()
            val result = (loading.execute(request) as SuccessResult).drawable

            return (result as BitmapDrawable).bitmap
        } catch (e: Exception) {
            throw DownloadImageException(context.getString(R.string.msg_download_image_error))
        }
    }
}

class DownloadImageException(message: String): Exception(message)