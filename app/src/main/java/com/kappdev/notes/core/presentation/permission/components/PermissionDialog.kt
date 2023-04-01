package com.kappdev.notes.core.presentation.permission.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.kappdev.notes.R
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider()

                Text(
                    text = stringResource(
                        if (isPermanentlyDeclined) R.string.btn_grant_permission else R.string.btn_ok_all_caps
                    ),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) onGoToAppSettingsClick() else onOkClick()
                        }
                        .padding(CustomTheme.spaces.medium)
                )
            }
        },
        title = {
            Text(text = stringResource(R.string.permission_dialog_title))
        },
        text = {
            Text(
                text = stringResource(
                    permissionTextProvider.getDescriptionId(isPermanentlyDeclined)
                )
            )
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescriptionId(isPermanentlyDeclined: Boolean): Int
}

class VibrationPermissionTextProvider: PermissionTextProvider {
    override fun getDescriptionId(isPermanentlyDeclined: Boolean): Int {
        return if (isPermanentlyDeclined)
            R.string.vibration_per_declined_text
        else
            R.string.vibration_per_explain_text
    }
}

class NotificationPolicyPermissionTextProvider: PermissionTextProvider {
    override fun getDescriptionId(isPermanentlyDeclined: Boolean): Int {
        return if (isPermanentlyDeclined)
            R.string.notification_policy_per_declined_text
        else
            R.string.notification_policy_per_explain_text
    }
}
