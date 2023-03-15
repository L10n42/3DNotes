package com.kappdev.notes.feature_notes.presentation.notes.components

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.kappdev.notes.feature_notes.presentation.notes.NotesBottomSheet
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.SelectFolderBottomSheet

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotesBottomSheetController(
    viewModel: NotesViewModel,
    currentSheet: NotesBottomSheet,
    sheetState: ModalBottomSheetState,
) {
    when(currentSheet) {
        is NotesBottomSheet.NewFolder -> {
            AddEditFolderSheet(
                close = { viewModel.closeSheet() },
                onDone = { name ->
                    viewModel.createFolder(name)
                }
            )
        }
        is NotesBottomSheet.SelectFolder -> {
            SelectFolderBottomSheet(
                folders = viewModel.allFolders,
                sheetState = sheetState,
                onClick = {
                    viewModel.moveSelectedTo(it)
                    viewModel.closeSheet()
                }
            )
        }
    }
}