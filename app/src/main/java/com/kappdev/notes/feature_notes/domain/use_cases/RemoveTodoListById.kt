package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class RemoveTodoListById(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(id: Long) {
        val todoList = repository.getTodoListById(id)
        repository.removeTodoListById(id)
        if (todoList.folderId != null && todoList.folderId > 0.toLong()) repository.decrementFolderItems(todoList.folderId)
    }
}