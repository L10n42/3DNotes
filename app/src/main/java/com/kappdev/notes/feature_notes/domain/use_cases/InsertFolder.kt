package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class InsertFolder(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(folder: Folder): Long {
        return repository.insertFolder(folder)
    }
}