package com.kappdev.notes.feature_notes.domain.repository

import com.kappdev.notes.feature_notes.data.data_source.TodoListDao
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun insertFolder(folder: Folder): Long

    fun getFoldersFlow(): Flow<List<Folder>>

    fun getFoldersList(): List<Folder>

    fun getFolderById(id: Long): Folder

    suspend fun incrementFolderItems(folderId: Long)

    suspend fun decrementFolderItems(folderId: Long)

    suspend fun updateFolder(folder: Folder): Int

    suspend fun removeFolder(folder: Folder): Int


    suspend fun insertNote(note: Note): Long

    fun getNotesFlow(): Flow<List<Note>>

    fun getNotesList(): List<Note>

    fun getNoteById(id: Long): Note

    fun getNotesByFolderId(id: Long): List<Note>

    fun removeNoteById(id: Long)

    fun removeNotesByFolderId(id: Long)

    suspend fun removeNote(note: Note): Int


    suspend fun insertTodoList(todoList: TodoList): Long

    fun getTodoListFlow(): Flow<List<TodoList>>

    fun getTodoLists(): List<TodoList>

    fun getTodoListById(id: Long): TodoList

    fun getTodoListsByFolderId(id: Long): List<TodoList>

    fun removeTodoListById(id: Long)

    fun removeTodoListsByFolderId(id: Long)

    suspend fun removeTodoList(todoList: TodoList): Int
}