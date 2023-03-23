package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.domain.model.AnnotatedNote
import com.kappdev.notes.feature_notes.domain.model.AnnotatedTodoList
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.notes.SearchViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchDialog(
    notesViewModel: NotesViewModel,
    searchViewModel: SearchViewModel,
) {
    val scope = rememberCoroutineScope()
    val searchResultList = searchViewModel.searchList
    val lastSearchValue = searchViewModel.lastSearchArg.value
    val isSearching = searchViewModel.isSearching.value
    var isVisible by remember { mutableStateOf(false) }

    val animatedPadding by animateDpAsState(
        targetValue = if (isVisible) 0.dp else CustomTheme.spaces.small,
        animationSpec = tween(durationMillis = AnimDuration)
    )
    val animatedBarAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = AnimDuration)
    )
    val animatedCornerRadius by animateDpAsState(
        targetValue = if (isVisible) 0.dp else 16.dp,
        animationSpec = tween(durationMillis = AnimDuration)
    )
    val animatedBackgroundAlpha by animateFloatAsState(
        targetValue = if (isVisible) CustomTheme.opacity.backgroundOpacity else 0f,
        animationSpec = tween(durationMillis = AnimDuration)
    )

    val finish: () -> Unit = {
        scope.launch {
            isVisible = false
            delay(AnimDuration.toLong())
            searchViewModel.clear()
            notesViewModel.switchSearchModeOFF()
        }
    }

    LaunchedEffect(key1 = true) { isVisible = true }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = CustomTheme.colors.surface.copy(alpha = animatedBackgroundAlpha)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(CustomTheme.spaces.small),
            contentPadding = PaddingValues(bottom = CustomTheme.spaces.small),
            modifier = Modifier.fillMaxSize()
        ) {
            stickyHeader {
                SearchBar(
                    isSearching = isSearching,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(animatedBarAlpha)
                        .padding(
                            top = animatedPadding,
                            start = animatedPadding,
                            end = animatedPadding
                        ),
                    shape = RoundedCornerShape(animatedCornerRadius),
                    onCancel = finish,
                    onSearch = { searchViewModel.search(it) }
                )
            }

            if (lastSearchValue.trim().isNotBlank() && searchResultList.isEmpty()) {
                item { UnableToFind() }
            }

            items(searchResultList) { item ->
                when (item) {
                    is AnnotatedNote -> AnnotatedNoteCard(
                        modifier = Modifier.padding(horizontal = CustomTheme.spaces.small),
                        annotatedNote = item
                    ){ id ->
                        notesViewModel.navigate(Screen.AddEditNote.route.plus("?noteId=$id"))
                    }

                    is AnnotatedTodoList -> AnnotatedTodoListCard(
                        modifier = Modifier.padding(horizontal = CustomTheme.spaces.small),
                        annotatedTodoList = item,
                    ) { id ->
                        notesViewModel.navigate(Screen.TodoList.route.plus("?todoListId=$id"))
                    }
                }
            }
        }
    }
}

private const val AnimDuration = 200