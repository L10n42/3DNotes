package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class GetFolderById(
    private val repository: NotesRepository
) {

    operator fun invoke(id: Long): Folder {
        return repository.getFolderById(id)
    }
}