package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class GetNotesByFolderId(
    private val repository: NotesRepository
) {

    operator fun invoke(id: Long): List<Note> {
        return repository.getNotesByFolderId(id)
    }
}