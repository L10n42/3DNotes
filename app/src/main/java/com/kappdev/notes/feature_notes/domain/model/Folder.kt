package com.kappdev.notes.feature_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "folders_table")
data class Folder(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val items: Int,
    val timestamp: Long
) {
    companion object {
        val EmptyFolder = Folder(
            id = 0,
            name = "",
            items = 0,
            timestamp = 0
        )
    }
}
