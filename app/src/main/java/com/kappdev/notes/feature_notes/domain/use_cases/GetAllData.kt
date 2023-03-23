package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class GetAllData(
    private val repository: NotesRepository
) {

    operator fun invoke(): List<Any> {
        val folders = repository.getFoldersList()

        val finalList = getTodoListsWithOutFolder() + getNotesWithOutFolder() + folders
        return finalList.sortedByDescending { item ->
            when (item) {
                is Note -> item.timestamp
                is Folder -> item.timestamp
                is TodoList -> item.timestamp
                else -> { null }
            }
        }
    }

    private fun getTodoListsWithOutFolder(): List<TodoList> {
        val todoLists = repository.getTodoLists()
        return todoLists.mapNotNull { todoList ->
            if (todoList.folderId == null) todoList else null
        }
    }

    private fun getNotesWithOutFolder(): List<Note> {
        val notes = repository.getNotesList()
        return notes.mapNotNull { note ->
            if (note.folderId == null) note else null
        }
    }
}