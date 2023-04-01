package com.kappdev.notes.feature_notes.domain.model

import androidx.compose.ui.text.AnnotatedString
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.domain.util.TodoConvertor
import java.time.LocalDateTime

@Entity(tableName = "todo_lists_table")
data class TodoList(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    @TypeConverters(TodoConvertor::class)
    val content: List<Todo>,
    val folderId: Long? = null,
    val timestamp: Long,
    @TypeConverters(AlarmContent::class)
    val alarm: LocalDateTime? = null
) {
    fun toAnnotated(name: AnnotatedString, content: List<AnnotatedTodo>): AnnotatedTodoList {
        return AnnotatedTodoList(
            id = this.id,
            name = name,
            content = content,
            folderId = this.folderId,
            timestamp = this.timestamp,
            alarm = this.alarm
        )
    }

    fun toStringList(includeName: Boolean = true): String {
        val builder = StringBuilder()
        if (includeName) builder.append(this.name + "\n")

        this.content.forEach { todo ->
            val todoText = "- " + todo.text + if (todo.checked) " (done)" else ""
            builder.append(todoText + "\n")
        }

        return builder.toString()
    }
}