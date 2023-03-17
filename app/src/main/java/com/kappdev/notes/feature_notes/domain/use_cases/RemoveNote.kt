package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class RemoveNote(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.removeNote(note)
        if (note.folderId != null && note.folderId > 0.toLong()) repository.decrementFolderItems(note.folderId)
    }
}