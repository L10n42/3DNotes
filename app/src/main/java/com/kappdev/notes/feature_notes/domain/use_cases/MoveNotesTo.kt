package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class MoveNotesTo(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(note: Note, folderId: Long) {
        val noteWithNewFolder = note.copy(folderId = folderId)
        repository.insertNote(noteWithNewFolder)
        repository.incrementFolderItems(folderId)
        if (note.folderId != null && note.folderId > 0.toLong()) repository.decrementFolderItems(note.folderId)
    }

    suspend operator fun invoke(noteId: Long, folderId: Long) {
        val note = repository.getNoteById(noteId)
        invoke(note, folderId)
    }

    suspend operator fun invoke(notes: List<Note>, folderId: Long) {
        notes.forEach { note ->
            invoke(note, folderId)
        }
    }
}