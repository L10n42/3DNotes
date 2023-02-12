package com.kappdev.notes.feature_notes.data.repository

import com.kappdev.notes.feature_notes.data.data_source.NoteDao
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val noteDao: NoteDao
): NotesRepository {

    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note)
    }

    override fun getNotes(): Flow<List<Note>> {
        return  noteDao.getNotes()
    }

    override fun getNoteById(id: Long): Note {
        return noteDao.getNoteById(id)
    }

    override suspend fun removeNote(note: Note): Long {
        return noteDao.removeNote(note)
    }
}