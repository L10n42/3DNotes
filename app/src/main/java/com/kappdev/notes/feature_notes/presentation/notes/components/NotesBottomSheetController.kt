package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.runtime.Composable
import com.kappdev.notes.feature_notes.presentation.notes.NotesBottomSheet
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel

@Composable
fun NotesBottomSheetController(
    viewModel: NotesViewModel,
    currentSheet: NotesBottomSheet,
    closeBS: () -> Unit
) {
    when(currentSheet) {
        is NotesBottomSheet.NewFolder -> {
            AddEditFolderSheet(close = closeBS) { name ->
                viewModel.createFolder(name)
            }
        }
    }
}