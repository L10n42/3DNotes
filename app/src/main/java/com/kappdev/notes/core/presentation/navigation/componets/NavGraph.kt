package com.kappdev.notes.core.presentation.navigation.componets

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.add_edit_note.components.AddEditNoteScreen
import com.kappdev.notes.feature_notes.presentation.folder_screen.components.FolderScreen
import com.kappdev.notes.feature_notes.presentation.notes.components.NotesScreen
import com.kappdev.notes.feature_notes.presentation.settings.components.SettingsScreen

@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Notes.route
    ) {
        composable(Screen.Settings.route) {
            SettingsScreen(navController)
        }

        composable(
            route = Screen.FolderScreen.route.plus("folderId={folderId}"),
            arguments = listOf(
                navArgument(name = "folderId") {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val id = it.arguments?.getLong("folderId") ?: 0
            FolderScreen(navController = navController, folderId = id)
        }

        composable(
            route = Screen.AddEditNote.route.plus("?noteId={noteId}&folderId={folderId}"),
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.LongType
                    defaultValue = 0
                },
                navArgument(name = "folderId") {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val id = it.arguments?.getLong("noteId") ?: 0
            val folderId = it.arguments?.getLong("folderId")
            AddEditNoteScreen(navController = navController, noteId = id, folderId = folderId)
        }

        composable(route = Screen.Notes.route) {
            NotesScreen(navController)
        }
    }
}