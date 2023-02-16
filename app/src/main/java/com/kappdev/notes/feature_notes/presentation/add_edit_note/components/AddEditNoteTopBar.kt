package com.kappdev.notes.feature_notes.presentation.add_edit_note.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kappdev.notes.feature_notes.presentation.add_edit_note.AddEditNoteViewModel

@Composable
fun AddEditNoteTopBar(
    viewModel: AddEditNoteViewModel,
    goToNotes: () -> Unit
) {
    val noteContent = viewModel.noteContent.value
    val undo = TopBarItem(icon = Icons.Default.Undo, id = "undo_btn", enable = true)
    val redo = TopBarItem(icon = Icons.Default.Redo, id = "redo_btn", enable = true)
    val more = TopBarItem(icon = Icons.Default.MoreVert, id = "more_btn", enable = true)
    val done = TopBarItem(icon = Icons.Default.Done, id = "done_btn", enable = noteContent.isNotBlank())
    val itemsList = listOf(undo, redo, done, more)

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
            itemsList.forEach { item ->
                IconButton(
                    enabled = item.enable,
                    onClick = {
                        when (item) {
                            undo -> viewModel.undo()
                            redo -> viewModel.redo()
                            more -> {}
                            done -> viewModel.save(onFinish = goToNotes)
                        }
                    }
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.id,
                        tint = if (item.enable) MaterialTheme.colors.surface else MaterialTheme.colors.background
                    )
                }
            }
        }
    )
}

private data class TopBarItem(val icon: ImageVector, val id: String, val enable: Boolean = true)