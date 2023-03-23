package com.kappdev.notes.feature_notes.domain.model

import androidx.compose.ui.text.AnnotatedString

data class AnnotatedTodo(
    val id: String,
    val text: AnnotatedString,
    val checked: Boolean,
)
