package com.kappdev.notes.feature_notes.presentation.folder_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.CustomDropDownMenu
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.folder_screen.FolderViewModel

@Composable
fun FolderScreenTopBar(
    title: String,
    viewModel: FolderViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.12f),
        navigationIcon = {
            IconButton(onClick = { viewModel.navigate(Screen.Notes.route) }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back button",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "folder screen more btn",
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
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                    }
                ) { Text(stringResource(R.string.title_remove)) }
            }
        }
    )
}