package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class RemoveNoteById(
    private val repository: NotesRepository
) {

    operator fun invoke(id: Long) {
        repository.removeNoteById(id)
    }
}