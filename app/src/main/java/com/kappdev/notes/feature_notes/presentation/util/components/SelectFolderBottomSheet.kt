package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.ui.custom_theme.CustomTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectFolderBottomSheet(
    folders: List<Folder>,
    sheetState: ModalBottomSheetState,
    onClick: (id: Long) -> Unit
) {
    val animatedShape by animateDpAsState(
        targetValue = if (sheetState.offset.value in 0f..10f) 0.dp else 16.dp,
        animationSpec = tween(durationMillis = 50)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CustomTheme.colors.surface,
                shape = RoundedCornerShape(topStart = animatedShape, topEnd = animatedShape)
            )
    ) {
        BottomSheetTitleHat(R.string.title_select_folder)

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(folders) { folder ->
                FolderCard(folder, onClick = onClick)
            }
        }
    }
}