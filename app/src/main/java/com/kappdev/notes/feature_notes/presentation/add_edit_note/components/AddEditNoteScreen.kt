package com.kappdev.notes.feature_notes.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.add_edit_note.AddEditNoteViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme

@ExperimentalMaterialApi
@Composable
fun AddEditNoteScreen(
    noteId: Long,
    folderId: Long?,
    navController: NavHostController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.setEditNoteId(noteId)
        if (folderId != null && folderId > 0) viewModel.setFolderId(folderId)
        if (noteId > 0) viewModel.getNoteById()
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = CustomTheme.colors.onSurface.copy(alpha = CustomTheme.opacity.scrimOpacity),
        sheetElevation = 0.dp,
        sheetContent = { Box(Modifier.height(1.dp)) }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            topBar = {
                AddEditNoteTopBar(viewModel) {
                    navController.navigate(Screen.Notes.route) { popUpTo(0) }
                }
            }
        ) {
            AddEditNoteContent(viewModel)
        }
    }
}
