package com.kappdev.notes.feature_notes.data.data_source

import androidx.room.*
import com.kappdev.notes.feature_notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Query("SELECT * FROM notes_table")
    fun getNotesFlow() : Flow<List<Note>>

    @Query("SELECT * FROM notes_table")
    fun getNotesList(): List<Note>

    @Query("SELECT * FROM notes_table WHERE folderId = :id")
    fun getNotesByFolderId(id: Long): List<Note>

    @Query("SELECT * FROM notes_table WHERE id = :id LIMIT 1")
    fun getNoteById(id: Long): Note

    @Query("DELETE FROM notes_table WHERE id = :id")
    fun removeNoteById(id: Long)

    @Delete
    suspend fun removeNote(note: Note): Int
}