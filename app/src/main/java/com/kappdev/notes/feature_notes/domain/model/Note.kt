package com.kappdev.notes.feature_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kappdev.notes.feature_notes.domain.util.NoteType

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val content: String,
    val folderId: Long? = null,
    val timestamp: Long
)

class InsertNoteException(message: String) : Exception(message)
class RemoveNoteException(message: String) : Exception(message)
