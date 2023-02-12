package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class GetNotes(
    private val repository: NotesRepository
) {

    operator fun invoke(): Flow<List<Note>> {
        return repository.getNotes()
    }
}