package com.kappdev.notes.feature_notes.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.PopupNumberPicker
import com.kappdev.notes.feature_notes.presentation.settings.SettingsViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun SelectVisibleLinesCard(
    viewModel: SettingsViewModel
) {
    val visibleLines = viewModel.visibleLines.value
    var isNumberChooserVisible by remember {
        mutableStateOf(false)
    }

    Card {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = CustomTheme.spaces.medium)
        ) {
            Text(
                text = stringResource(R.string.setting_title_lines_are_visible),
                fontSize = 18.sp,
                color = CustomTheme.colors.onSurface
            )

            Box {
                Text(
                    text = visibleLines.toString(),
                    textDecoration = TextDecoration.Underline,
                    fontSize = 18.sp,
                    color = CustomTheme.colors.onSurface,
                    modifier = Modifier.clickable {
                        isNumberChooserVisible = !isNumberChooserVisible
                    }
                )

                PopupNumberPicker(
                    expanded = isNumberChooserVisible,
                    range = 1..30,
                    selectedValue = visibleLines,
                    onValueChange = {
                        viewModel.setVisibleLines(it)
                    },
                    dismiss = { isNumberChooserVisible = false }
                )
            }
        }
    }
}
