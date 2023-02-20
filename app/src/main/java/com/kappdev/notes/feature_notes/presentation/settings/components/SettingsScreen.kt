package com.kappdev.notes.feature_notes.presentation.settings.components

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent,
        topBar = {
            SettingsTopBar {
                navController.navigate(Screen.Notes.route)
            }
        }
    ) {

    }
}