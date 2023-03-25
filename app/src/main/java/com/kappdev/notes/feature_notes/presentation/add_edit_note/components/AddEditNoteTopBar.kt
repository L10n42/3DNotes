package com.kappdev.notes.feature_notes.presentation.add_edit_note.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Undo
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.BackButton
import com.kappdev.notes.core.presentation.components.ConfirmDialog
import com.kappdev.notes.feature_notes.presentation.add_edit_note.AddEditNoteViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.MorePopupBtn
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun AddEditNoteTopBar(
    viewModel: AddEditNoteViewModel
) {
    val context = LocalContext.current
    val backStack = viewModel.textBackStack
    val currentStack = viewModel.currentStack
    val noteId = viewModel.currentNoteId.value

    val redo = TopBarItem(icon = Icons.Default.Redo, id = "redo_btn", enable = (currentStack < backStack.lastIndex))
    val undo = TopBarItem(icon = Icons.Default.Undo, id = "undo_btn", enable = (backStack.isNotEmpty() && currentStack > 0 && viewModel.noteChanged()))
    val done = TopBarItem(icon = Icons.Default.Done, id = "done_btn", enable = viewModel.noteIsNotBlank() && viewModel.noteChanged())

    var showRemoveDialog by remember { mutableStateOf(false) }
    if (showRemoveDialog) {
        ConfirmDialog(
            title = stringResource(R.string.title_remove),
            message = stringResource(R.string.confirm_remove_note_msg),
            confirmText = stringResource(R.string.btn_remove),
            closeDialog = { showRemoveDialog = false },
            onConfirm = { viewModel.removeNoteAndNavigateBack() }
        )
    }

    TopAppBar(
        title = {},
        elevation = 0.dp,
        backgroundColor = CustomTheme.colors.transparentSurface,
        modifier = Modifier
            .padding(all = CustomTheme.spaces.small)
            .clip(CustomTheme.shapes.large),
        navigationIcon = {
            BackButton { viewModel.navigateBack() }
        },
        actions = {
            TopBarBtn(undo) { viewModel.undo() }
            TopBarBtn(redo) { viewModel.redo() }
            TopBarBtn(done) { viewModel.save() }

            if (noteId > 0) {
                MorePopupBtn(
                    titlesResIds = listOf(R.string.title_copy, R.string.title_move_to, R.string.title_share, R.string.title_remove),
                    onItemClick = { id ->
                        when(id) {
                            R.string.title_remove -> showRemoveDialog = true
                            R.string.title_copy -> viewModel.copyNote(context)
                            R.string.title_move_to -> viewModel.openBottomSheet()
                            R.string.title_share -> viewModel.shareNote()
                        }
                    }
                )
            }
        }
    )
}

@Composable
private fun TopBarBtn(item: TopBarItem, onClick: () -> Unit) {
    IconButton(
        enabled = item.enable,
        onClick = onClick
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.id,
            tint = if (item.enable) CustomTheme.colors.onSurface else CustomTheme.colors.onSurface.copy(alpha = 0.5f)
        )
    }
}

private data class TopBarItem(val icon: ImageVector, val id: String, val enable: Boolean = true)