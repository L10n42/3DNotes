package com.kappdev.notes.feature_notes.domain.model

import androidx.compose.ui.text.AnnotatedString
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDateTime

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val content: String,
    val folderId: Long? = null,
    val timestamp: Long,
    @TypeConverters(AlarmContent::class)
    val alarm: LocalDateTime? = null
) {
    fun toAnnotated(title: AnnotatedString, content: AnnotatedString): AnnotatedNote {
        return AnnotatedNote(
            id = this.id,
            timestamp = this.timestamp,
            folderId = this.folderId,
            title = title,
            content = content,
            alarm = this.alarm
        )
    }
}