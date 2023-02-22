package com.kappdev.notes.feature_notes.presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isThemeDark = viewModel.theme.value

    Scaffold(
        backgroundColor = Color.Transparent,
        topBar = {
            SettingsTopBar(
                onBackClick = { navController.navigate(Screen.Notes.route) }
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(all = 16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
           item {
               SwitchCard(
                   titleResId = R.string.switch_theme_title,
                   isChecked = isThemeDark,
                   onCheckedChange = {
                       viewModel.changeTheme(it)
                   }
               )
           }
        }
    }
}

@Composable
private fun SwitchCard(
    titleResId: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = stringResource(titleResId),
                fontSize = 18.sp,
                color = MaterialTheme.colors.onSurface
            )

            Switch(
                checked = isChecked,
                onCheckedChange = { newValue ->
                    onCheckedChange(newValue)
                },
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = MaterialTheme.colors.background,
                    checkedThumbColor = MaterialTheme.colors.primary,
                    checkedTrackColor = MaterialTheme.colors.primary,
                    checkedTrackAlpha = 0.3f
                )
            )
        }
    }
}

@Composable
private fun Card(content: @Composable () -> Unit) {
    Surface(
        color = MaterialTheme.colors.surface.copy(alpha = 0.12f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        content = content
    )
}



















