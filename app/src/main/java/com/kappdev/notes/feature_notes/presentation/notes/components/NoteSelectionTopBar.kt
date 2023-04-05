package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.CustomBar
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun NoteSelectionTopBar(
    isVisible: Boolean,
    viewModel: NotesViewModel
) {
    val selectAllIconColor by animateColorAsState(
        targetValue = if (viewModel.selectedAll()) CustomTheme.colors.primary else CustomTheme.colors.onTopBarColor,
        animationSpec = tween(easing = LinearEasing)
    )

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically { size -> -size },
        exit = fadeOut() + slideOutVertically { size -> -size }
    ) {
        CustomBar(
            horizontalArrangement = Arrangement.SpaceBetween,
            background = CustomTheme.colors.topBarColor
        ) {
            IconButton(
                onClick = {
                    viewModel.switchSelectionModeOffAndClear()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close select mode button",
                    tint = CustomTheme.colors.onTopBarColor
                )
            }

            SelectedItems(items = viewModel.selectionList.size)

            IconButton(
                onClick = {
                    if (viewModel.selectedAll()) viewModel.clearSelection() else viewModel.selectAllItems()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_triple_checklist),
                    contentDescription = "select all items button",
                    tint = selectAllIconColor
                )
            }
        }
    }
}

@Composable
private fun SelectedItems(items: Int) {
    val text = when {
        items > 1 -> stringResource(id = R.string.selected_items, items)
        items == 1 -> stringResource(id = R.string.selected_item, items)
        else -> stringResource(id = R.string.select_items)
    }

    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = CustomTheme.colors.onTopBarColor
    )
}