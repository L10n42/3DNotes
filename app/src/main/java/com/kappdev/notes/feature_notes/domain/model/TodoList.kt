package com.kappdev.notes.feature_notes.domain.model

import android.text.Spannable
import android.text.SpannedString
import android.text.style.StrikethroughSpan
import androidx.compose.ui.text.AnnotatedString
import androidx.core.text.buildSpannedString
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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

    fun toSpannedString(): SpannedString {
        return buildSpannedString {
            var lastPoint = 0
            content.reversed().forEachIndexed { index, todo ->
                this.append(todo.text)

                if (todo.checked) {
                    this.setSpan(StrikethroughSpan(), lastPoint, lastPoint + todo.text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                lastPoint += todo.text.length
                if (index != content.lastIndex) this.append("\n")
            }
        }
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