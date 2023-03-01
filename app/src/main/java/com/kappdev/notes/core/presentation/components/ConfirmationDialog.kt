package com.kappdev.notes.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    cancelText: String = stringResource(R.string.btn_cancel),
    confirmText: String = stringResource(R.string.btn_ok),
    closeDialog: () -> Unit,
    onCancel: () -> Unit = closeDialog,
    onConfirm: () -> Unit,
) {
    val buttonPadding = PaddingValues(end = CustomTheme.spaces.medium, bottom = CustomTheme.spaces.medium)
    AlertDialog(
        onDismissRequest = closeDialog,
        shape = CustomTheme.shapes.medium,
        backgroundColor = CustomTheme.colors.surface,
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = CustomTheme.colors.onSurface
            )
        },
        text = {
            Text(
                text = message,
                fontSize = 16.sp,
                color = CustomTheme.colors.onSurface
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmText,
                    fontSize = 16.sp,
                    color = CustomTheme.colors.primary,
                    modifier = Modifier.padding(buttonPadding)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(
                    text = cancelText,
                    fontSize = 16.sp,
                    color = CustomTheme.colors.primary,
                    modifier = Modifier.padding(buttonPadding)
                )
            }
        }
    )
}