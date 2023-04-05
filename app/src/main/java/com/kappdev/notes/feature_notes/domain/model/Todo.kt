package com.kappdev.notes.feature_notes.domain.model

import android.text.Spannable
import android.text.SpannedString
import android.text.style.StrikethroughSpan
import androidx.compose.ui.text.AnnotatedString
import androidx.core.text.buildSpannedString

data class Todo(
    val id: String,
    val text: String,
    val checked: Boolean,
) {
    fun toAnnotated(text: AnnotatedString): AnnotatedTodo {
        return AnnotatedTodo(
            id = this.id,
            checked = this.checked,
            text = text
        )
    }

    fun toSpannedString(): SpannedString {
        return buildSpannedString {
            this.append(text)

            if (checked) {
                this.setSpan(StrikethroughSpan(), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }
}
