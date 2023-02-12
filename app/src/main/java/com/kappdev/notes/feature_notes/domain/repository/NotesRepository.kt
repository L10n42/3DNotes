package com.kappdev.notes.feature_notes.domain.repository

import com.kappdev.notes.feature_notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun insertNote(note: Note): Long

    fun getNotes(): Flow<List<Note>>

    fun getNoteById(id: Long): Note

    suspend fun removeNote(note: Note): Int
}