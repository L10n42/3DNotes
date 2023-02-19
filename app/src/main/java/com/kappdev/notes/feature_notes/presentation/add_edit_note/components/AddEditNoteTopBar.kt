package com.kappdev.notes.feature_notes.presentation.add_edit_note.components

import android.widget.Toast
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.ConfirmDialog
import com.kappdev.notes.core.presentation.components.CustomDropDownMenu
import com.kappdev.notes.feature_notes.presentation.add_edit_note.AddEditNoteViewModel

@Composable
fun AddEditNoteTopBar(
    viewModel: AddEditNoteViewModel,
    goToNotes: () -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var showRemoveDialog by remember { mutableStateOf(false) }
    val noteId = viewModel.currentNoteId.value
    val currentStack = viewModel.currentStack
    val backStack = viewModel.textBackStack

    val undo = TopBarItem(icon = Icons.Default.Undo, id = "undo_btn", enable = (backStack.isNotEmpty() && currentStack > 0))
    val redo = TopBarItem(icon = Icons.Default.Redo, id = "redo_btn", enable = (currentStack < backStack.lastIndex))
    val more = TopBarItem(icon = Icons.Default.MoreVert, id = "more_btn", enable = true)
    val done = TopBarItem(icon = Icons.Default.Done, id = "done_btn", enable = viewModel.noteIsNotBlank() && viewModel.noteChanged())

    if (showRemoveDialog) {
        ConfirmDialog(
            title = stringResource(R.string.title_remove),
            message = stringResource(R.string.confirm_remove_msg),
            confirmText = stringResource(R.string.btn_remove),
            closeDialog = { showRemoveDialog = false },
            onConfirm = { viewModel.removeNote(onFinish = goToNotes) }
        )
    }

    TopAppBar(
        title = {},
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        navigationIcon = {
            IconButton(onClick = goToNotes) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back button",
                    tint = MaterialTheme.colors.surface
                )
            }
        },
        actions = {
            TopBarBtn(undo) { viewModel.undo() }
            TopBarBtn(redo) { viewModel.redo() }
            TopBarBtn(done) {
                viewModel.save(
                    onFailure = { Toast.makeText(context, R.string.msg_could_not_save , Toast.LENGTH_SHORT).show() },
                    onSuccess = { Toast.makeText(context, R.string.msg_saved, Toast.LENGTH_SHORT).show() }
                )
            }

            if (noteId > 0) {
                TopBarBtn(more) { expanded = true }

                val menuPadding = with(LocalDensity.current) { 16.dp.roundToPx() }
                CustomDropDownMenu(
                    expanded = expanded,
                    dismiss = { expanded = false },
                    modifier = Modifier.width(140.dp),
                    offset = IntOffset(-menuPadding, menuPadding)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            showRemoveDialog = true
                        }
                    ) { Text(stringResource(R.string.title_remove)) }
                }
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
            tint = if (item.enable) MaterialTheme.colors.surface else MaterialTheme.colors.surface.copy(alpha = 0.5f)
        )
    }
}

private data class TopBarItem(val icon: ImageVector, val id: String, val enable: Boolean = true)