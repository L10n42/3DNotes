package com.kappdev.notes.feature_notes.domain.use_cases

data class NotesUseCases(
    val insertNote: InsertNote,
    val getNotes: GetNotes,
    val getNoteById: GetNoteById,
    val removeNote: RemoveNote
)
