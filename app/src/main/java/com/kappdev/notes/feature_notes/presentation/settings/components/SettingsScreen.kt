package com.kappdev.notes.feature_notes.presentation.settings.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.LoadingDialog
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.settings.SettingsViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val storageImages = viewModel.imagesUris
    val isLoading = viewModel.isLoading.value
    val isThemeDark = viewModel.theme.value
    val backgroundOpacity = viewModel.backgroundOpacity.value

    LaunchedEffect(key1 = true) { viewModel.onScreenLoading() }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = CustomTheme.colors.onSurface.copy(alpha = CustomTheme.opacity.scrimOpacity),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            SelectBackgroundSheet(viewModel, sheetState)
        }
    ) {
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

                item {
                    TextCard(titleResId = R.string.select_background_title) {
                        if (storageImages.isNotEmpty()) {
                            scope.launch { sheetState.show() }
                        } else viewModel.makeToast(R.string.msg_capture_image_error)
                    }
                }
            }

            if (isLoading) LoadingDialog()
        }
    }
}

@Composable
private fun TextCard(
    titleResId: Int,
    onClick: () -> Unit
) {
    Card(onClick = onClick) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = CustomTheme.spaces.medium),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(titleResId),
                fontSize = 18.sp,
                color = CustomTheme.colors.onSurface
            )
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Card(
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Surface(
        color = CustomTheme.colors.transparentSurface,
        shape = CustomTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        content = content
    )
}



















