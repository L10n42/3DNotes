package com.kappdev.notes.feature_notes.domain.model

import androidx.compose.ui.text.AnnotatedString
import java.time.LocalDateTime

data class AnnotatedNote(
    val id: Long,
    val title: AnnotatedString,
    val content: AnnotatedString,
    val folderId: Long? = null,
    val timestamp: Long,
    val alarm: LocalDateTime? = null
)
