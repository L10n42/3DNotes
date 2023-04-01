package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.TodoList

class ShareTodoList(
    private val shareText: ShareText
) {

    operator fun invoke(todoList: TodoList) {
        shareText(todoList.toStringList())
    }
}