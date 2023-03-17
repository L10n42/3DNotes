package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class GetTodoListById(
    private val repository: NotesRepository
) {

    operator fun invoke(id: Long): TodoList {
        return repository.getTodoListById(id)
    }
}