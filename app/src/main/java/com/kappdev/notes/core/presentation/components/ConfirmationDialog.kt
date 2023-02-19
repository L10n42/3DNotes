package com.kappdev.notes.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R

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
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )
        },
        text = {
            Text(
                text = message,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmText,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(
                    text = cancelText,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                )
            }
        }
    )
}