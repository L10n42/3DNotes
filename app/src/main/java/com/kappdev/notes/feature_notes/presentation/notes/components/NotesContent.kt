package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.notes.SearchViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.FolderCard
import com.kappdev.notes.feature_notes.presentation.util.components.NoteCard
import com.kappdev.notes.feature_notes.presentation.util.components.TodoListCard
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun NotesContent(
    viewModel: NotesViewModel,
    searchViewModel: SearchViewModel
) {
    val dataList = viewModel.data
    val selectionList = viewModel.selectionList
    val isSelectionModeOn = viewModel.selectionMode.value
    val searchValue = searchViewModel.lastSearchArg.value

    val listAnimatedPadding by animateDpAsState(
        targetValue = if (isSelectionModeOn) CustomTheme.spaces.extraLarge else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

    AnimatedVisibility(
        visible = searchValue.isBlank(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(CustomTheme.spaces.small),
            contentPadding = PaddingValues(all = CustomTheme.spaces.small),
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = listAnimatedPadding)
        ) {
            item {
                NoteTopBar(isVisible = !isSelectionModeOn, viewModel) {
                    viewModel.switchSearchModeON()
                }
            }

            items(dataList) { content ->
                when (content) {
                    is Note -> {
                        NoteCard(
                            note = content,
                            visibleLines = viewModel.visibleLines,
                            selected = selectionList.contains(content) && isSelectionModeOn,
                            onLongClick = {
                                if (!isSelectionModeOn) viewModel.switchSelectionModeOnAndSelect(content)
                            },
                            onClick = { id ->
                                if (isSelectionModeOn) {
                                    viewModel.switchItemSelection(content)
                                } else {
                                    viewModel.navigate(Screen.AddEditNote.route.plus("?noteId=$id"))
                                }
                            }
                        )
                    }
                    is Folder -> {
                        FolderCard(
                            folder = content,
                            selected = selectionList.contains(content) && isSelectionModeOn,
                            onLongClick = {
                                if (!isSelectionModeOn) viewModel.switchSelectionModeOnAndSelect(content)
                            },
                            onClick = { id ->
                                if (isSelectionModeOn) {
                                    viewModel.switchItemSelection(content)
                                } else {
                                    viewModel.navigate(Screen.FolderScreen.route.plus("folderId=$id"))
                                }
                            }
                        )
                    }
                    is TodoList -> {
                        TodoListCard(
                            todoList = content,
                            visibleLines = viewModel.visibleLines,
                            selected = selectionList.contains(content) && isSelectionModeOn,
                            onLongClick = {
                                if (!isSelectionModeOn) viewModel.switchSelectionModeOnAndSelect(content)
                            },
                            onClick = { id ->
                                if (isSelectionModeOn) {
                                    viewModel.switchItemSelection(content)
                                } else {
                                    viewModel.navigate(Screen.TodoList.route.plus("?todoListId=$id"))
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}