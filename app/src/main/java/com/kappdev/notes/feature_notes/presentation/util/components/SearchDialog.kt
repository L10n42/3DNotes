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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.notes.SearchViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme
import com.kappdev.notes.ui.theme.ErrorRed
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
    val searchArg = searchViewModel.lastSearchArg.value
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(animatedBarAlpha)
                        .padding(
                            top = animatedPadding,
                            start = animatedPadding,
                            end = animatedPadding
                        ),
                    shape = RoundedCornerShape(animatedCornerRadius),
                    onCancel = finish
                ) {
                    searchViewModel.search(it)
                }
            }

            items(searchResultList) { note ->
                val annotatedNote = note.toAnnotated(
                    title = highlightIn(text = note.title, highlightValue = searchArg),
                    content = highlightIn(text = note.content, highlightValue = searchArg)
                )
                AnnotatedNoteCard(
                    modifier = Modifier.padding(horizontal = CustomTheme.spaces.small),
                    noteWithAnnotation = annotatedNote
                ){ id ->
                    notesViewModel.navigate(Screen.AddEditNote.route.plus("?noteId=$id"))
                }
            }
        }
    }
}

private fun highlightIn(text: String, highlightValue: String): AnnotatedString {

    val highlightStyle = SpanStyle(
        color = ErrorRed
    )
    return buildAnnotatedString {
        text.sliceBy(highlightValue).forEach { textPiece ->
            if (textPiece == highlightValue) {
                withStyle(highlightStyle) { append(textPiece) }
            } else append(textPiece)
        }
    }
}

private fun String.sliceBy(separator: String): List<String> {
    var text = this
    val list = mutableListOf<String>()

    while (text.isNotBlank()) {
        if (text.startsWith(separator)) {
            list.add(separator)
            text = text.removePrefix(separator)
        }

        val value = text.substringBefore(separator, text)
        if (value == text) {
            list.add(value)
            text = text.removePrefix(value)
        } else {
            list.addAll(listOf(value, separator))
            text = text.removePrefix(value + separator)
        }
    }

    return list
}

private const val AnimDuration = 200