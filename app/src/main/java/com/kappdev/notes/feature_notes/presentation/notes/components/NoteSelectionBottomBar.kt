package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.ConfirmDialog
import com.kappdev.notes.feature_notes.presentation.notes.NotesBottomSheet
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.CustomBar
import com.kappdev.notes.feature_notes.presentation.util.components.DividerPosition
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun NoteSelectionBottomBar(
    isVisible: Boolean,
    viewModel: NotesViewModel
) {
    var showRemoveDialog by remember { mutableStateOf(false) }
    if (showRemoveDialog) {
        ConfirmDialog(
            title = stringResource(R.string.title_remove),
            message = stringResource(R.string.confirm_remove_all_items_msg),
            confirmText = stringResource(R.string.btn_remove),
            closeDialog = { showRemoveDialog = false },
            onConfirm = {
                viewModel.removeSelectedAndReset()
            }
        )
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically { size -> size },
        exit = fadeOut() + slideOutVertically { size -> size }
    ) {
        CustomBar(
            height = 64.dp,
            background = CustomTheme.colors.bottomBarColor,
            dividerPosition = DividerPosition.TOP,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(CustomTheme.spaces.medium)
            ) {
                Button(icon = Icons.Outlined.Delete, titleResId = R.string.btn_remove) {
                    showRemoveDialog = true
                }

                if (viewModel.foldersNotSelected()) {
                    Button(icon = Icons.Filled.Logout, titleResId = R.string.btn_move_to) {
                        viewModel.openSheet(NotesBottomSheet.SelectFolder)
                    }
                }
            }
        }
    }
}

@Composable
private fun Button(
    icon: ImageVector,
    titleResId: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(
            interactionSource = MutableInteractionSource(),
            indication = null,
            onClick = onClick
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = CustomTheme.colors.onBottomBarColor
        )

        Text(
            text = stringResource(titleResId),
            fontSize = 14.sp,
            color = CustomTheme.colors.onBottomBarColor
        )
    }
}