package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.CustomBar
import com.kappdev.notes.feature_notes.presentation.util.components.DividerPosition
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun NoteSelectionBottomBar(
    isVisible: Boolean,
    viewModel: NotesViewModel
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically { size -> size },
        exit = fadeOut() + slideOutVertically { size -> size }
    ) {
        CustomBar(
            height = 64.dp,
            background = CustomTheme.colors.background,
            dividerPosition = DividerPosition.TOP
        ) {
            Button(icon = Icons.Outlined.Delete, titleResId = R.string.btn_remove) {

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
            tint = CustomTheme.colors.onSurface
        )

        Text(
            text = stringResource(titleResId),
            fontSize = 14.sp,
            color = CustomTheme.colors.onSurface
        )
    }
}