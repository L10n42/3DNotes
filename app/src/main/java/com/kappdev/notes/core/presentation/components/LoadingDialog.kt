package com.kappdev.notes.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = { /* TODO */ },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = CustomTheme.colors.surface,
            elevation = 8.dp
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = CustomTheme.colors.primary,
                    modifier = Modifier.padding(all = CustomTheme.spaces.small)
                )
            }
        }
    }
}