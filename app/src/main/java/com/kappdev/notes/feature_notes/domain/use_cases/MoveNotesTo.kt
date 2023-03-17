package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class MoveNotesTo(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(folderId: Long, note: Note) {
        val noteWithNewFolder = note.copy(folderId = folderId)
        repository.insertNote(noteWithNewFolder)

        repository.incrementFolderItems(folderId)
        if (note.folderId != null && note.folderId  > 0.toLong())
            repository.decrementFolderItems(note.folderId )
    }

    suspend operator fun invoke(folderId: Long, noteId: Long) {
        val note = repository.getNoteById(noteId)
        invoke(folderId, note)
    }

    suspend operator fun invoke(folderId: Long, notes: List<Note>) {
        notes.forEach { note ->
            invoke(folderId, note)
        }
    }
}