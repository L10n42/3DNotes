package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class GetAllData(
    private val repository: NotesRepository
) {

    operator fun invoke(): List<Any> {
        val notes = repository.getNotesList()
        val notesWithoutFolder = notes.mapNotNull { note ->
            if (note.folderId == null) note else null
        }
        val folders = repository.getFoldersList()
        val finalList = notesWithoutFolder + folders
        return finalList.sortedByDescending { item ->
            when (item) {
                is Note -> item.timestamp
                is Folder -> item.timestamp
                else -> { null }
            }
        }
    }
}