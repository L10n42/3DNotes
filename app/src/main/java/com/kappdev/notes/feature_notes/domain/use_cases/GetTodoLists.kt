package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class GetTodoLists(
    private val repository: NotesRepository
) {

    operator fun invoke(): Flow<List<TodoList>> {
        return repository.getTodoListFlow()
    }
}