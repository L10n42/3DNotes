package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kappdev.notes.core.presentation.components.CustomDropDownMenu

@Composable
fun MorePopupBtn(
    titlesResIds: List<Int>,
    onItemClick: (titleResId: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "more btn",
            tint = MaterialTheme.colors.onSurface
        )
    }

    val menuPadding = with(LocalDensity.current) { 16.dp.roundToPx() }
    CustomDropDownMenu(
        expanded = expanded,
        dismiss = { expanded = false },
        modifier = Modifier.width(140.dp),
        offset = IntOffset(-menuPadding, menuPadding)
    ) {
        titlesResIds.forEach { titleResId ->
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onItemClick(titleResId)
                }
            ) {
                Text(text = stringResource(titleResId))
            }
        }
    }
}