package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class GetAllDataByFolder(
    private val repository: NotesRepository
) {

    operator fun invoke(folderId: Long): List<Any> {
        val notes = repository.getNotesByFolderId(folderId)
        val todoLists = repository.getTodoListsByFolderId(folderId)

        val finalList = notes + todoLists
        return finalList.sortedByDescending { item ->
            when (item) {
                is Note -> item.timestamp
                is TodoList -> item.timestamp
                else -> { null }
            }
        }
    }
}