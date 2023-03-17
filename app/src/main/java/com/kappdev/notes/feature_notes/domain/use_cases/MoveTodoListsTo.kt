package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class MoveTodoListsTo(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(folderId: Long, todoList: TodoList) {
        val noteWithNewFolder = todoList.copy(folderId = folderId)
        repository.insertTodoList(noteWithNewFolder)

        repository.incrementFolderItems(folderId)
        if (todoList.folderId != null && todoList.folderId  > 0.toLong())
            repository.decrementFolderItems(todoList.folderId )
    }

    suspend operator fun invoke(folderId: Long, todoListId: Long) {
        val todoList = repository.getTodoListById(todoListId)
        invoke(folderId, todoList)
    }

    suspend operator fun invoke(folderId: Long, todoLists: List<TodoList>) {
        todoLists.forEach { todoList ->
            invoke(folderId, todoList)
        }
    }
}