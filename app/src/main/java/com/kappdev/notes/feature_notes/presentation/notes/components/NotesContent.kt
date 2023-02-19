package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kappdev.notes.core.presentation.components.LazyColumnScrollDirection
import com.kappdev.notes.core.presentation.components.LazyColumnWithScrollIndicator
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel

@Composable
fun NotesContent(
    viewModel: NotesViewModel
) {
    var scrollingToTop by remember { mutableStateOf(false) }
    val dataList = viewModel.data

    LazyColumnWithScrollIndicator(
        verticalArrangement = Arrangement.spacedBy(ListItemsPadding),
        contentPadding = PaddingValues(all = ListItemsPadding),
        modifier = Modifier.fillMaxSize(),
        onScroll = { direction ->
            scrollingToTop = when (direction) {
                LazyColumnScrollDirection.TOP -> true
                LazyColumnScrollDirection.BOTTOM -> false
            }
        }
    ) {
        items(dataList) { content ->
            when (content) {
                is Note -> {
                    NoteCard(content) { id ->
                        viewModel.navigate(Screen.AddEditNote.route.plus("?noteId=$id"))
                    }
                }
                is Folder -> {
                    FolderCard(content) { id ->
                        viewModel.navigate(Screen.FolderScreen.route.plus("folderId=$id"))
                    }
                }
            }
        }
    }
}

private val ListItemsPadding = 8.dp