package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.kappdev.notes.R
import com.kappdev.notes.ui.custom_theme.CustomTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun PickDateAndTime(
    initialTime: LocalDateTime = LocalDateTime.now(),
    onCancel: () -> Unit,
    onPicked: (pickedTime: LocalDateTime) -> Unit
) {
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf(LocalTime.now()) }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    fun returnValue() {
        onCancel()
        val exactTime = pickedTime.withSecond(0).withNano(0)
        onPicked(LocalDateTime.of(pickedDate, exactTime))
    }

    LaunchedEffect(key1 = initialTime) {
        pickedTime = initialTime.toLocalTime()
        pickedDate = initialTime.toLocalDate()
        dateDialogState.show()
    }
    
    MaterialDialog(
        dialogState = dateDialogState,
        backgroundColor = CustomTheme.colors.surface,
        onCloseRequest = { onCancel() },
        buttons = {
            positiveButton(
                text = stringResource(R.string.btn_ok),
                textStyle = TextStyle(color = CustomTheme.colors.primary)
            ) {
                timeDialogState.show()
            }
            negativeButton(
                text = stringResource(R.string.btn_cancel),
                textStyle = TextStyle(color = CustomTheme.colors.primary),
                onClick = onCancel
            )
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = stringResource(R.string.pick_date_title),
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = CustomTheme.colors.primary,
                headerTextColor = CustomTheme.colors.onPrimary,
                calendarHeaderTextColor = CustomTheme.colors.onPrimary,
                dateActiveBackgroundColor = CustomTheme.colors.primary,
                dateInactiveBackgroundColor = Color.Transparent,
                dateInactiveTextColor = CustomTheme.colors.onSurface,
                dateActiveTextColor = CustomTheme.colors.onPrimary
            )
        ) {
            pickedDate = it
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        backgroundColor = CustomTheme.colors.surface,
        onCloseRequest = {
            onCancel()
        },
        buttons = {
            positiveButton(
                text = stringResource(R.string.btn_ok),
                textStyle = TextStyle(color = CustomTheme.colors.primary),
            ) {
                returnValue()
            }
            negativeButton(
                text = stringResource(R.string.btn_cancel),
                textStyle = TextStyle(color = CustomTheme.colors.primary),
                onClick = onCancel
            )
        }
    ) {
        timepicker(
            initialTime = LocalTime.now(),
            title = stringResource(R.string.pick_time_title),
            colors = TimePickerDefaults.colors(
                activeBackgroundColor = CustomTheme.colors.primary,
                inactiveBackgroundColor = CustomTheme.colors.background,
                activeTextColor = CustomTheme.colors.onPrimary,
                inactiveTextColor = CustomTheme.colors.onSurface,
                selectorColor = CustomTheme.colors.primary,
                selectorTextColor = CustomTheme.colors.onPrimary,
                headerTextColor = CustomTheme.colors.onSurface,
                borderColor = CustomTheme.colors.onSurface
            )
        ) {
            pickedTime = it
        }
    }
}