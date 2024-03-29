package com.kappdev.notes.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kappdev.notes.feature_notes.data.data_source.NotesDatabase
import com.kappdev.notes.feature_notes.data.repository.AndroidAlarmSchedule
import com.kappdev.notes.feature_notes.data.repository.NotesRepositoryImpl
import com.kappdev.notes.feature_notes.data.repository.StorageRepositoryImpl
import com.kappdev.notes.feature_notes.domain.repository.AlarmSchedule
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
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @Named("viewModelDatabase")
    @ViewModelScoped
    fun provideNotesDatabase(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Named("viewModelNotesRepository")
    @ViewModelScoped
    fun provideNotesRepository(@Named("viewModelDatabase")db: NotesDatabase): NotesRepository {
        return NotesRepositoryImpl(db.noteDao, db.folderDao, db.todoListDao)
    }

    @Provides
    @ViewModelScoped
    fun provideNotesUseCases(
        @Named("viewModelNotesRepository") repository: NotesRepository,
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
            getTodoLists = GetTodoLists(repository),
            removeTodoListById = RemoveTodoListById(repository)
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

    @Provides
    @ViewModelScoped
    fun provideTextShare(@ApplicationContext appContext: Context): ShareText {
        return ShareText(appContext)
    }

    @Provides
    @ViewModelScoped
    fun provideTodoListShare(shareText: ShareText): ShareTodoList {
        return ShareTodoList(shareText)
    }

    @Provides
    @ViewModelScoped
    fun provideAlarmSchedule(@ApplicationContext appContext: Context): AlarmSchedule {
        return AndroidAlarmSchedule(appContext)
    }
}