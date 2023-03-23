package com.kappdev.notes.feature_notes.domain.model

import androidx.compose.ui.text.AnnotatedString

data class AnnotatedTodoList(
    val id: Long,
    val name: AnnotatedString,
    val content: List<AnnotatedTodo>,
    val folderId: Long? = null,
    val timestamp: Long
)
