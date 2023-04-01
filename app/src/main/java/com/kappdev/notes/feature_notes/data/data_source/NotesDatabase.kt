package com.kappdev.notes.feature_notes.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.util.AlarmConvertor
import com.kappdev.notes.feature_notes.domain.util.TodoConvertor

@Database(
    entities = [Note::class, Folder::class, TodoList::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(TodoConvertor::class, AlarmConvertor::class)
abstract class NotesDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "notes_database"
    }

    abstract val noteDao: NoteDao
    abstract val folderDao: FolderDao
    abstract val todoListDao: TodoListDao
}