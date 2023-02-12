package com.kappdev.notes.feature_notes.domain.use_cases

import android.content.Context
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.domain.model.InsertNoteException
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository

class InsertNote(
    private val repository: NotesRepository,
    private val context: Context
) {

    @Throws(InsertNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) throw InsertNoteException(context.getString(R.string.error_empty_note_content))

        val result = repository.insertNote(note)

        if (result < 0) throw InsertNoteException(context.getString(R.string.error_empty_note_content))
    }
}