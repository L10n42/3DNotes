package com.kappdev.notes.core.presentation.navigation

sealed class Screen(val route: String) {
    object Notes: Screen("notes_screen")
}
