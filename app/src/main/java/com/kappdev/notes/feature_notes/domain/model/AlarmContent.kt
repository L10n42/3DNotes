package com.kappdev.notes.feature_notes.domain.model

data class AlarmContent(
    val id: Long,
    val type: AlarmContentType
)

enum class AlarmContentType { Note, TodoList }
