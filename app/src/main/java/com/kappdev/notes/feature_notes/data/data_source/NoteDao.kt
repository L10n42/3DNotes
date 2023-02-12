package com.kappdev.notes.feature_notes.data.data_source

import androidx.room.*
import com.kappdev.notes.feature_notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Query("SELECT * FROM notes_table")
    fun getNotes() : Flow<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id = :id LIMIT 1")
    fun getNoteById(id: Long): Note

    @Delete
    suspend fun removeNote(note: Note): Long
}