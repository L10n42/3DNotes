package com.kappdev.notes.feature_notes.data.repository

import com.kappdev.notes.feature_notes.data.data_source.FolderDao
import com.kappdev.notes.feature_notes.data.data_source.NoteDao
import com.kappdev.notes.feature_notes.data.data_source.TodoListDao
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val noteDao: NoteDao,
    private val folderDao: FolderDao,
    private val todoListDao: TodoListDao
): NotesRepository {

    override suspend fun insertFolder(folder: Folder): Long {
        return folderDao.insertFolder(folder)
    }

    override fun getFoldersFlow(): Flow<List<Folder>> {
        return folderDao.getFoldersFlow()
    }

    override fun getFoldersList(): List<Folder> {
        return folderDao.getFoldersList()
    }

    override fun getFolderById(id: Long): Folder {
        return folderDao.getFolderById(id)
    }

    override suspend fun incrementFolderItems(folderId: Long) {
        val folder = folderDao.getFolderById(folderId)
        val newItemsCount = folder.items + 1
        folderDao.updateFolder(folder.copy(items = newItemsCount))
    }

    override suspend fun decrementFolderItems(folderId: Long) {
        val folder = folderDao.getFolderById(folderId)
        val newItemsCount = folder.items - 1
        folderDao.updateFolder(folder.copy(items = newItemsCount))
    }

    override suspend fun updateFolder(folder: Folder): Int {
        return folderDao.updateFolder(folder)
    }

    override suspend fun removeFolder(folder: Folder): Int {
        return folderDao.removeFolder(folder)
    }

    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note)
    }

    override fun getNotesFlow(): Flow<List<Note>> {
        return  noteDao.getNotesFlow()
    }

    override fun getNotesList(): List<Note> {
        return noteDao.getNotesList()
    }

    override fun getNoteById(id: Long): Note {
        return noteDao.getNoteById(id)
    }

    override fun getNotesByFolderId(id: Long): List<Note> {
        return noteDao.getNotesByFolderId(id)
    }

    override fun removeNoteById(id: Long) {
        noteDao.removeNoteById(id)
    }

    override fun removeNotesByFolderId(id: Long) {
        noteDao.removeNotesByFolderId(id)
    }

    override suspend fun removeNote(note: Note): Int {
        return noteDao.removeNote(note)
    }

    override suspend fun insertTodoList(todoList: TodoList): Long {
        return todoListDao.insertTodoList(todoList)
    }

    override fun getTodoListFlow(): Flow<List<TodoList>> {
        return todoListDao.getTodoListsFlow()
    }

    override fun getTodoLists(): List<TodoList> {
        return todoListDao.getTodoLists()
    }

    override fun getTodoListById(id: Long): TodoList {
        return todoListDao.getTodoListById(id)
    }

    override fun getTodoListsByFolderId(id: Long): List<TodoList> {
        return todoListDao.getTodoListsByFolderId(id)
    }

    override fun removeTodoListById(id: Long) {
        todoListDao.removeTodoListById(id)
    }

    override fun removeTodoListsByFolderId(id: Long) {
        todoListDao.removeTodoListsByFolderId(id)
    }

    override suspend fun removeTodoList(todoList: TodoList): Int {
        return todoListDao.removeTodoList(todoList)
    }
}