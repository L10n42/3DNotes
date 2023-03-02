package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.notes.core.presentation.components.LazyColumnScrollDirection
import com.kappdev.notes.core.presentation.components.LazyColumnWithScrollIndicator
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.SearchBar
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun NotesContent(
    viewModel: NotesViewModel
) {
    val listState = rememberLazyListState()
    var scrollingToTop by remember { mutableStateOf(false) }
    val dataList = viewModel.data

    LazyColumnWithScrollIndicator(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(CustomTheme.spaces.small),
        contentPadding = PaddingValues(all = CustomTheme.spaces.small),
        modifier = Modifier.fillMaxSize(),
        onScroll = { direction ->
            scrollingToTop = when (direction) {
                LazyColumnScrollDirection.TOP -> true
                LazyColumnScrollDirection.BOTTOM -> false
            }
        }
    ) {
        item {
            SearchBar(search = {}, viewModel)
        }

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