package com.kappdev.notes.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kappdev.notes.core.data.repository.SettingRepositoryImpl
import com.kappdev.notes.core.domain.repository.SettingRepository
import com.kappdev.notes.feature_notes.data.data_source.NotesDatabase
import com.kappdev.notes.feature_notes.data.repository.NotesRepositoryImpl
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    @Named("singletonDatabase")
    fun provideNotesDatabaseForAlarmRec(app: Application): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    @Named("singletonNotesRepository")
    fun provideNotesRepositoryForAlarmRec(@Named("singletonDatabase") db: NotesDatabase): NotesRepository {
        return NotesRepositoryImpl(db.noteDao, db.folderDao, db.todoListDao)
    }

    @Singleton
    @Provides
    fun provideSetting(@ApplicationContext appContext: Context): SettingRepository {
        return SettingRepositoryImpl(appContext)
    }
}