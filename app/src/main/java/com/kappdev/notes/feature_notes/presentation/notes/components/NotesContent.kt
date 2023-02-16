package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.notes.core.presentation.components.LazyColumnScrollDirection
import com.kappdev.notes.core.presentation.components.LazyColumnWithScrollIndicator
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel

@Composable
fun NotesContent(
    viewModel: NotesViewModel
) {
    var scrollingToTop by remember { mutableStateOf(false) }
    val databaseNotes = viewModel.notes.value

    LazyColumnWithScrollIndicator(
        verticalArrangement = Arrangement.spacedBy(ListItemsPadding),
        contentPadding = PaddingValues(vertical = ListItemsPadding),
        modifier = Modifier.fillMaxSize(),
        onScroll = { direction ->
            scrollingToTop = when (direction) {
                LazyColumnScrollDirection.TOP -> true
                LazyColumnScrollDirection.BOTTOM -> false
            }
        }
    ) {
        items(databaseNotes) { content ->
            when (content) {
                is Note -> {
                    NoteCard(
                        note = content,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                }
//                is Folder -> {
//                    FolderCard(
//                        folder = content,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 8.dp)
//                    )
//                }
            }
        }
    }
}
private val ListItemsPadding = 8.dp