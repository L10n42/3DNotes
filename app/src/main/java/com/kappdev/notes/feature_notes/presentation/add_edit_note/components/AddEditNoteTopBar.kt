package com.kappdev.notes.feature_notes.presentation.add_edit_note.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kappdev.notes.feature_notes.presentation.add_edit_note.AddEditNoteViewModel

@Composable
fun AddEditNoteTopBar(
    viewModel: AddEditNoteViewModel,
    goBack: () -> Unit
) {
    TopAppBar(
        title = {},
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        navigationIcon = {
            IconButton(onClick = goBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back button",
                    tint = MaterialTheme.colors.surface
                )
            }
        },
        actions = {
            TopBarItem.ItemsList.forEach { item ->
                IconButton(
                    onClick = {
                        when (item) {
                            TopBarItem.Undo -> viewModel.undo()
                            TopBarItem.Redo -> viewModel.redo()
                            TopBarItem.More -> {}
                            TopBarItem.Done -> viewModel.save()
                        }
                    }
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.id,
                        tint = MaterialTheme.colors.surface
                    )
                }
            }
        }
    )
}

private data class TopBarItem(val icon: ImageVector, val id: String) {
    companion object {
        val Undo = TopBarItem(Icons.Default.Undo, "undo_btn")
        val Redo = TopBarItem(Icons.Default.Redo, "redo_btn")
        val More = TopBarItem(Icons.Default.MoreVert, "more_btn")
        val Done = TopBarItem(Icons.Default.Done, "done_btn")
        val ItemsList = listOf(Undo, Redo, Done, More)
    }
}