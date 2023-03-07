package com.kappdev.notes.feature_notes.presentation.folder_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.notes.core.presentation.components.LazyColumnScrollDirection
import com.kappdev.notes.core.presentation.components.LazyColumnWithScrollIndicator
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.presentation.folder_screen.FolderViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.NoteCard
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun FolderContent(
    viewModel: FolderViewModel
) {
    val dataList = emptyList<Any>()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(CustomTheme.spaces.small),
        contentPadding = PaddingValues(all = CustomTheme.spaces.small),
        modifier = Modifier.fillMaxSize()
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