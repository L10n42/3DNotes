package com.kappdev.notes.core.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIos,
            contentDescription = "back button",
            tint = CustomTheme.colors.onSurface
        )
    }
}