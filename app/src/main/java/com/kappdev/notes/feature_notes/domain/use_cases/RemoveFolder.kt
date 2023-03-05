package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class RemoveFolder(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(folder: Folder) {
        val result = repository.removeFolder(folder)
        if (result > 0) {
            repository.removeNotesByFolderId(folder.id)
        }
    }
}