package com.kappdev.notes.feature_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kappdev.notes.feature_notes.domain.util.NoteType

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val type: NoteType,
    val title: String,
    val content: String,
    val timestamp: Long
) {
    companion object {
        val EmptyNote = Note(
            id = 0,
            type = NoteType.SHEET,
            title = "",
            content = "",
            timestamp = 0
        )
    }
}

class InsertNoteException(message: String) : Exception(message)
class RemoveNoteException(message: String) : Exception(message)
