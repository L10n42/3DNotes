package com.kappdev.notes.feature_notes.presentation.folder_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.notes.core.presentation.components.LazyColumnScrollDirection
import com.kappdev.notes.core.presentation.components.LazyColumnWithScrollIndicator
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.presentation.folder_screen.FolderViewModel
import com.kappdev.notes.feature_notes.presentation.notes.components.FolderCard
import com.kappdev.notes.feature_notes.presentation.notes.components.NoteCard

@Composable
fun FolderContent(
    viewModel: FolderViewModel
) {
    var scrollingToTop by remember { mutableStateOf(false) }
    val dataList = emptyList<Any>()

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
                    NoteCard(note = content) {

                    }
                }
            }
        }
    }
}

private val ListItemsPadding = 8.dp