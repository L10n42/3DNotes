package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.BackgroundImage
import com.kappdev.notes.core.presentation.components.LazyColumnScrollDirection
import com.kappdev.notes.core.presentation.components.LazyColumnWithScrollIndicator
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.util.NoteType
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel

@Composable
fun NotesContent(
    viewModel: NotesViewModel
) {
    var scrollingToTop by remember { mutableStateOf(false) }

    val notes = listOf(
        Folder(
            id = 1,
            name = "Some folder. with very long name to test how does it work, and even longer than old name so here we go",
            items = 12,
            timestamp = 1676209419
        ),
        Note(
            id = 0,
            title = "Some thoughts",
            content = "thought number 1, I don't know something is here",
            timestamp = 1676209419
        ),
        Note(
            id = 1,
            title = "Some stuff here.",
            content = "I need to write something here, so here we go.",
            timestamp = 1676209485
        )
    )

    val backgroundImage = painterResource(R.drawable.background_image_1)

    BackgroundImage(
        image = backgroundImage,
        modifier = Modifier.fillMaxSize()
    ) {
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
            items(notes) { content ->
                when (content) {
                    is Note -> {
                        NoteCard(
                            note = content,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )
                    }
                    is Folder -> {
                        FolderCard(
                            folder = content,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
private val ListItemsPadding = 8.dp