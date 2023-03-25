package com.kappdev.notes.feature_notes.domain.use_cases

import android.content.Context
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.domain.model.TodoList

class ShareTodoList(
    private val shareText: ShareText,
    private val context: Context
) {

    operator fun invoke(todoList: TodoList) {
        shareText(buildTodoString(todoList))
    }

    private fun buildTodoString(todoList: TodoList): String {
        val builder = StringBuilder()
        val labelFinished = context.getString(R.string.label_finished)
        builder.append(todoList.name + "\n")

        todoList.content.forEach { todo ->
            val todoText = "- " + todo.text + if (todo.checked) " $labelFinished" else ""
            builder.append(todoText + "\n")
        }

        return builder.toString()
    }
}