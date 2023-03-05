package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class GetFolders(
    private val repository: NotesRepository
) {
    fun flow() = repository.getFoldersFlow()

    fun list() = repository.getFoldersList()
}