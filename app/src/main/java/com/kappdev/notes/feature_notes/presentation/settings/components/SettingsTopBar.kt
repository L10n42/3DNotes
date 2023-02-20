package com.kappdev.notes.feature_notes.presentation.settings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Composable
fun SettingsTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.settings_screen_label),
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
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back button",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    )
}