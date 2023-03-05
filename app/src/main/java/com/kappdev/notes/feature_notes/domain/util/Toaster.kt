package com.kappdev.notes.feature_notes.domain.util

import android.content.Context
import android.widget.Toast

class Toaster(
    private val context: Context
) {

    fun makeToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, msg, duration).show()
    }

    fun makeToast(msgRes: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, context.getString(msgRes), duration).show()
    }
}