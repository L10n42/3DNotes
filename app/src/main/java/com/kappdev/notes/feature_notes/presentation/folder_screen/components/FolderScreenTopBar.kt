package com.kappdev.notes.feature_notes.presentation.folder_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.BackButton
import com.kappdev.notes.core.presentation.components.ConfirmDialog
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.folder_screen.FolderViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.MorePopupBtn
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun FolderScreenTopBar(
    title: String,
    viewModel: FolderViewModel
) {
    var showRemoveDialog by remember { mutableStateOf(false) }
    if (showRemoveDialog) {
        ConfirmDialog(
            title = stringResource(R.string.title_remove),
            message = stringResource(R.string.confirm_remove_folder_msg),
            confirmText = stringResource(R.string.btn_remove),
            closeDialog = { showRemoveDialog = false },
            onConfirm = {
                viewModel.removeFolder()
                viewModel.navigate(Screen.Notes.route)
            }
        )
    }

    TopAppBar(
        modifier = Modifier
            .padding(CustomTheme.spaces.small)
            .clip(CustomTheme.shapes.large),
        elevation = 0.dp,
        backgroundColor = CustomTheme.colors.transparentSurface,
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = CustomTheme.colors.onSurface,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            BackButton { viewModel.navigate(Screen.Notes.route) }
        },
        actions = {
            MorePopupBtn(
                titlesResIds = listOf(R.string.title_edit, R.string.title_remove),
                onItemClick = { id ->
                    when(id) {
                        R.string.title_remove -> showRemoveDialog = true
                        R.string.title_edit -> viewModel.openBottomSheet()
                    }
                }
            )
        }
    )
}