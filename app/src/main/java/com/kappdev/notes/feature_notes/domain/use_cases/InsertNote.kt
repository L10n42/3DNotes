package com.kappdev.notes.feature_notes.domain.use_cases

import android.content.Context
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class InsertNote(
    private val repository: NotesRepository,
    private val context: Context
) {

    suspend operator fun invoke(note: Note): Long {
        var insertNote = note
        if (note.title.trim().isBlank()) {
            insertNote = note.copy(title = context.getString(R.string.untitled))
        }
        val result = repository.insertNote(insertNote)
        if (result > 0 && note.folderId != null && note.id == 0.toLong()) repository.incrementFolderItems(note.folderId)

        return result
    }
}