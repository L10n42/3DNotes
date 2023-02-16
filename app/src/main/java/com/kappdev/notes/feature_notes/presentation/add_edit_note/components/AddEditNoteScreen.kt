package com.kappdev.notes.feature_notes.presentation.add_edit_note.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.add_edit_note.AddEditNoteViewModel

@ExperimentalMaterialApi
@Composable
fun AddEditNoteScreen(
    navController: NavHostController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.16f),
        sheetElevation = 0.dp,
        sheetContent = { Box(Modifier.height(1.dp)) }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            topBar = {
                AddEditNoteTopBar(viewModel) {
                    navController.navigate(Screen.Notes.route)
                }
            }
        ) {
            AddEditNoteContent(viewModel)
        }
    }
}
