package com.kappdev.notes.feature_notes.data.data_source

import androidx.room.*
import com.kappdev.notes.feature_notes.domain.model.TodoList
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoList(todoList: TodoList): Long

    @Query("SELECT * FROM todo_lists_table")
    fun getTodoListsFlow(): Flow<List<TodoList>>

    @Query("SELECT * FROM todo_lists_table")
    fun getTodoLists(): List<TodoList>

    @Query("SELECT * FROM todo_lists_table WHERE folderId = :id")
    fun getTodoListsByFolderId(id: Long): List<TodoList>

    @Query("SELECT * FROM todo_lists_table WHERE id = :id LIMIT 1")
    fun getTodoListById(id: Long): TodoList

    @Query("DELETE FROM todo_lists_table WHERE folderId = :id")
    fun removeTodoListsByFolderId(id: Long)

    @Query("DELETE FROM todo_lists_table WHERE id = :id")
    fun removeTodoListById(id: Long)

    @Delete
    suspend fun removeTodoList(todoList: TodoList): Int
}