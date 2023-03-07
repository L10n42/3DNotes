package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.MorePopupBtn
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun NoteTopBar(
    viewModel: NotesViewModel,
    onClick: () -> Unit
) {
    Surface(
        color = CustomTheme.colors.transparentSurface,
        shape = CustomTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search_icon",
                    tint = CustomTheme.colors.onSurface,
                    modifier = Modifier.padding(start = CustomTheme.spaces.medium)
                )

                TextButton(onClick = onClick) {
                    Text(
                        text = stringResource(R.string.hint_search),
                        fontSize = 18.sp,
                        maxLines = 1,
                        color = CustomTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(start = CustomTheme.spaces.small)
                            .fillMaxWidth(0.85f)
                    )
                }
            }

            MorePopupBtn(
                titlesResIds = listOf(R.string.settings_label),
                onItemClick = { id ->
                    when(id) {
                        R.string.settings_label -> viewModel.navigate(Screen.Settings.route)
                    }
                }
            )
        }
    }
}