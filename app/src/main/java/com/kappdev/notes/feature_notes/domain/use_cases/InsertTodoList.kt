package com.kappdev.notes.feature_notes.domain.use_cases

import android.content.Context
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class InsertTodoList(
    private val repository: NotesRepository,
    private val context: Context
) {

    suspend operator fun invoke(todoList: TodoList): Long {
        var insertTodoList = todoList
        if (todoList.name.trim().isBlank()) {
            insertTodoList = todoList.copy(name = context.getString(R.string.unnamed))
        }
        val result = repository.insertTodoList(insertTodoList)
        if (result > 0 && todoList.folderId != null && todoList.id == 0.toLong()) repository.incrementFolderItems(todoList.folderId)

        return result
    }
}