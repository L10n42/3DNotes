package com.kappdev.notes.feature_notes.domain.use_cases

import android.content.Context
import android.content.Intent
import com.kappdev.notes.R

class ShareText(
    private val context: Context,

) {

    operator fun invoke(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)

        val sheetTitle = context.getString(R.string.title_share_via)
        val chooserIntent = Intent.createChooser(intent, sheetTitle)
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }
}