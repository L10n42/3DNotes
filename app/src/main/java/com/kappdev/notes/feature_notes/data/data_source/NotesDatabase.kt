package com.kappdev.notes.feature_notes.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList

@Database(
    entities = [Note::class, Folder::class, TodoList::class],
    version = 4,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "notes_database"
    }

    abstract val noteDao: NoteDao
    abstract val folderDao: FolderDao
    abstract val todoListDao: TodoListDao
}