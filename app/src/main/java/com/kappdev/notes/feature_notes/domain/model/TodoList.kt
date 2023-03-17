package com.kappdev.notes.feature_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_lists_table")
data class TodoList(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val content: String,
    val folderId: Long? = null,
    val timestamp: Long
)