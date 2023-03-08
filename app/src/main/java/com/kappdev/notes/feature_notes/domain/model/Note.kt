package com.kappdev.notes.feature_notes.domain.model

import androidx.compose.ui.text.AnnotatedString
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val content: String,
    val folderId: Long? = null,
    val timestamp: Long
) {
    fun toAnnotated(title: AnnotatedString, content: AnnotatedString): NoteWithAnnotation {
        return NoteWithAnnotation(
            id = this.id,
            timestamp = this.timestamp,
            folderId = this.folderId,
            title = title,
            content = content
        )
    }
}

data class NoteWithAnnotation(
    val id: Long,
    val title: AnnotatedString,
    val content: AnnotatedString,
    val folderId: Long? = null,
    val timestamp: Long
)

class InsertNoteException(message: String) : Exception(message)
class RemoveNoteException(message: String) : Exception(message)
