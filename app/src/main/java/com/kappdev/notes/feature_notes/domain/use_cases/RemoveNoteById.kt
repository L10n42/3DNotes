package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class RemoveNoteById(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(id: Long) {
        val note = repository.getNoteById(id)
        repository.removeNoteById(id)
        if (note.folderId != null && note.folderId > 0.toLong()) repository.decrementFolderItems(note.folderId)
    }
}