package com.kappdev.notes.core.presentation.components

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
            Button(text = confirmText) {
                closeDialog()
                onConfirm()
            }
        },
        dismissButton = {
            Button(text = cancelText, onClick = onCancel)
        }
    )
}

@Composable
private fun Button(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(end = CustomTheme.spaces.small, bottom = CustomTheme.spaces.small)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = CustomTheme.colors.primary
        )
    }
}