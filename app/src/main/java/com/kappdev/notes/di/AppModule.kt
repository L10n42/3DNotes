package com.kappdev.notes.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kappdev.notes.core.data.repository.SettingRepositoryImpl
import com.kappdev.notes.core.domain.repository.SettingRepository
import com.kappdev.notes.feature_notes.data.data_source.NotesDatabase
import com.kappdev.notes.feature_notes.data.repository.NotesRepositoryImpl
import com.kappdev.notes.feature_notes.data.repository.StorageRepositoryImpl
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository
import com.kappdev.notes.feature_notes.domain.repository.StorageRepository
import com.kappdev.notes.feature_notes.domain.use_cases.*
import com.kappdev.notes.feature_notes.domain.util.Toaster
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideNotesDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @ViewModelScoped
    fun provideNotesRepository(db: NotesDatabase): NotesRepository {
        return NotesRepositoryImpl(db.noteDao, db.folderDao, db.todoListDao)
    }

    @Provides
    @ViewModelScoped
    fun provideSetting(@ApplicationContext appContext: Context): SettingRepository {
        return SettingRepositoryImpl(appContext)
    }

    @Provides
    @ViewModelScoped
    fun provideNotesUseCases(
        repository: NotesRepository,
        @ApplicationContext appContext: Context
    ): NotesUseCases {
        return NotesUseCases(
            getAllData = GetAllData(repository),
            getAllDataByFolder = GetAllDataByFolder(repository),
            moveNotesTo = MoveNotesTo(repository),
            moveTodoListsTo = MoveTodoListsTo(repository),
            insertNote = InsertNote(repository, appContext),
            insertFolder = InsertFolder(repository),
            getNotes = GetNotes(repository),
            getNoteById = GetNoteById(repository),
            getFolderById = GetFolderById(repository),
            getNotesByFolderId = GetNotesByFolderId(repository),
            getFolders = GetFolders(repository),
            removeFolder = RemoveFolder(repository),
            removeNote = RemoveNote(repository),
            removeNoteById = RemoveNoteById(repository),
            multipleRemove = MultipleRemove(repository),
            insertTodoList = InsertTodoList(repository, appContext),
            getTodoListById = GetTodoListById(repository),
            getTodoLists = GetTodoLists(repository)
        )
    }

    @Provides
    @ViewModelScoped
    fun provideToaster(@ApplicationContext appContext: Context): Toaster {
        return Toaster(appContext)
    }

    @Provides
    @ViewModelScoped
    fun provideStorageRepository(): StorageRepository {
        return StorageRepositoryImpl()
    }

    @Provides
    @ViewModelScoped
    fun provideImageLoader(@ApplicationContext appContext: Context): DownloadImage {
        return DownloadImage(appContext)
    }
}