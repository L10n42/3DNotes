package com.kappdev.notes.feature_notes.domain.use_cases

import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class MultipleRemove(
    private val repository: NotesRepository
) {

    suspend fun removeAll(list: List<Any>) {
        list.forEach { item ->
            when (item) {
                is Note -> removeNote(item)
                is Folder -> removeFolder(item)
                is TodoList -> removeTodoList(item)
            }
        }
    }

    private suspend fun removeTodoList(todoList: TodoList) {
        repository.removeTodoList(todoList)
        if (todoList.folderId != null && todoList.folderId > 0.toLong()) repository.decrementFolderItems(todoList.folderId)
    }

    private suspend fun removeFolder(folder: Folder) {
        val result = repository.removeFolder(folder)
        if (result > 0) repository.removeNotesByFolderId(folder.id)
        if (result > 0) repository.removeTodoListsByFolderId(folder.id)
    }

    private suspend fun removeNote(note: Note) {
        repository.removeNote(note)
        if (note.folderId != null && note.folderId > 0.toLong()) repository.decrementFolderItems(note.folderId)
    }
}