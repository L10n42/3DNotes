package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.CustomDropDownMenu
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun NotesTopBar(
    goToSettings: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "3DNotes",
                color = CustomTheme.colors.surface,
                fontWeight = FontWeight.Bold
            )
        },
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        actions = {
            IconButton(
                onClick = { expanded = true }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "notes screen more btn",
                    tint = CustomTheme.colors.surface
                )
            }

            val menuPadding = with(LocalDensity.current) { 16.dp.roundToPx() }
            CustomDropDownMenu(
                expanded = expanded,
                dismiss = { expanded = false },
                modifier = Modifier.width(140.dp),
                offset = IntOffset(-menuPadding, menuPadding)
            ) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        goToSettings()
                    }
                ) { Text(stringResource(R.string.settings_label)) }
            }
        }
    )
}