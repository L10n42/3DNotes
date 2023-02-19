package com.kappdev.notes.core.presentation.navigation

sealed class Screen(val route: String) {
    object Notes: Screen("notes_screen")
    object AddEditNote: Screen("add_edit_note_screen")
    object FolderScreen: Screen("folder_screen")
}
