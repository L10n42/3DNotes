package com.kappdev.notes.feature_notes.data.data_source

import androidx.room.*
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: Folder): Long

    @Query("SELECT * FROM folders_table")
    fun getFolders() : Flow<List<Folder>>

    @Query("SELECT * FROM folders_table WHERE id = :id LIMIT 1")
    fun getFolderById(id: Long): Folder

    @Delete
    suspend fun removeFolder(folder: Folder): Int
}