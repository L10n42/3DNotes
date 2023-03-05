package com.kappdev.notes.feature_notes.domain.use_cases

import android.content.Context
import android.util.Log
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.RemoveNoteException
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class RemoveNote(
    private val repository: NotesRepository,
    private val context: Context
) {

    @Throws(RemoveNoteException::class)
    suspend operator fun invoke(note: Note) {
        val result = repository.removeNote(note)
        if (note.folderId != null && note.folderId > 0.toLong()) repository.decrementFolderItems(note.folderId)

        if (result < 0) throw RemoveNoteException(context.getString(R.string.error_could_not_remove_note))
    }
}