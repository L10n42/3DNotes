package com.kappdev.notes.feature_notes.presentation.notes

sealed class NotesBottomSheet {
    object NewFolder: NotesBottomSheet()
    object SelectFolder: NotesBottomSheet()
}
