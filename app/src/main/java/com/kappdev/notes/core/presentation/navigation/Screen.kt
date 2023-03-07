package com.kappdev.notes.core.presentation.navigation

sealed class Screen(val route: String) {
    object Notes: Screen("notes_screen")
    object SearchNote: Screen("search_note_screen")
    object AddEditNote: Screen("add_edit_note_screen")
    object FolderScreen: Screen("folder_screen")
    object Settings: Screen("settings_screen")
}
