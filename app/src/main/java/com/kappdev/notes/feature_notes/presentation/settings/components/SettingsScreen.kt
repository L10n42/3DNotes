package com.kappdev.notes.feature_notes.presentation.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isThemeDark = viewModel.theme.value
    val backgroundOpacity = viewModel.backgroundOpacity.value

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

            item {
                SliderCard(
                    titleResId = R.string.setting_title_opacity,
                    value = backgroundOpacity,
                    onValueChange = { viewModel.changeBackgroundOpacity(it) },
                    onValueChangeFinish = { viewModel.saveBackgroundOpacity() }
                )
            }
        }
    }
}

@Composable
private fun SliderCard(
    titleResId: Int,
    value: Float,
    onValueChange: (value: Float) -> Unit,
    onValueChangeFinish: () -> Unit
) {
    val valueInPercentages = (value * 100).toInt()

    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(titleResId) + " - ${valueInPercentages}%",
                fontSize = 18.sp,
                color = CustomTheme.colors.onSurface
            )

            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = 0f..1f,
                onValueChangeFinished = onValueChangeFinish,
                steps = 0,
                colors = SliderDefaults.colors(
                    thumbColor = CustomTheme.colors.primary,
                    activeTrackColor = CustomTheme.colors.primary
                )
            )
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
                color = CustomTheme.colors.onSurface
            )

            Switch(
                checked = isChecked,
                onCheckedChange = { newValue ->
                    onCheckedChange(newValue)
                },
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = CustomTheme.colors.background,
                    checkedThumbColor = CustomTheme.colors.primary,
                    checkedTrackColor = CustomTheme.colors.primary,
                    checkedTrackAlpha = 0.3f
                )
            )
        }
    }
}

@Composable
private fun Card(
    viewModel: SettingsViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    Surface(
        color = CustomTheme.colors.surface.copy(alpha = viewModel.getBackgroundOpacity()),
        shape = CustomTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        content = content
    )
}



















