package com.kappdev.notes.feature_notes.domain.use_cases

data class NotesUseCases(
    val getAllData: GetAllData,
    val insertNote: InsertNote,
    val insertFolder: InsertFolder,
    val getNotes: GetNotes,
    val getNoteById: GetNoteById,
    val removeNote: RemoveNote,
    val removeNoteById: RemoveNoteById
)
