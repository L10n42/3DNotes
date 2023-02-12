package com.kappdev.notes.core.presentation.navigation.componets

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.notes.components.NotesScreen

@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Notes.route
    ) {
        composable(route = Screen.Notes.route) {
            NotesScreen(navController)
        }
    }
}