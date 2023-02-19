package com.kappdev.notes.feature_notes.data.repository

import com.kappdev.notes.feature_notes.data.data_source.FolderDao
import com.kappdev.notes.feature_notes.data.data_source.NoteDao
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val noteDao: NoteDao,
    private val folderDao: FolderDao,
): NotesRepository {

    override suspend fun insertFolder(folder: Folder): Long {
        return folderDao.insertFolder(folder)
    }

    override fun getFoldersFlow(): Flow<List<Folder>> {
        return folderDao.getFoldersFlow()
    }

    override fun getFoldersList(): List<Folder> {
        return folderDao.getFoldersList()
    }

    override fun getFolderById(id: Long): Folder {
        return folderDao.getFolderById(id)
    }

    override suspend fun removeFolder(folder: Folder): Int {
        return folderDao.removeFolder(folder)
    }

    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note)
    }

    override fun getNotesFlow(): Flow<List<Note>> {
        return  noteDao.getNotesFlow()
    }

    override fun getNotesList(): List<Note> {
        return noteDao.getNotesList()
    }

    override fun getNoteById(id: Long): Note {
        return noteDao.getNoteById(id)
    }

    override fun removeNoteById(id: Long) {
        noteDao.removeNoteById(id)
    }

    override suspend fun removeNote(note: Note): Int {
        return noteDao.removeNote(note)
    }
}