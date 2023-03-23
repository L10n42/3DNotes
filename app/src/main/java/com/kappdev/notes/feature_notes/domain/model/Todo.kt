package com.kappdev.notes.feature_notes.domain.model

import androidx.compose.ui.text.AnnotatedString

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
}
