package com.kappdev.notes.feature_notes.presentation.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.domain.model.ImageShade
import com.kappdev.notes.feature_notes.domain.util.ShadeColor
import com.kappdev.notes.feature_notes.presentation.settings.SettingsViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun ShadeSelectorCard(
    imageShade: ImageShade,
    viewModel: SettingsViewModel
) {
    val valueInPercentages = (imageShade.opacity * 100).toInt()

    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.setting_title_shade) + " - ${valueInPercentages}%",
                    fontSize = 18.sp,
                    color = CustomTheme.colors.onSurface
                )

                ColorChooser(
                    color = imageShade.color,
                    onColorChange = {
                        viewModel.setImageShadeColor(it)
                        viewModel.saveImageShade()
                    }
                )
            }

            Slider(
                value = imageShade.opacity,
                onValueChange = { viewModel.setImageShadeOpacity(it) },
                valueRange = 0f..1f,
                onValueChangeFinished = { viewModel.saveImageShade() },
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
private fun ColorChooser(
    color: ShadeColor,
    onColorChange: (color: ShadeColor) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(CustomTheme.spaces.small)
    ) {
        ColorRectangle(color = Color.White, selected = color == ShadeColor.White) {
            onColorChange(ShadeColor.White)
        }
        ColorRectangle(color = Color.Black, selected = color == ShadeColor.Black) {
            onColorChange(ShadeColor.Black)
        }
    }
}

@Composable
private fun ColorRectangle(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Spacer(
        modifier = Modifier
            .size(24.dp)
            .background(color = color, shape = CustomTheme.shapes.small)
            .clickable { onClick() }
            .border(
                width = if (selected) 2.dp else 0.dp,
                color = if (selected) CustomTheme.colors.primary else Color.Transparent,
                shape = CustomTheme.shapes.small
            )
    )
}

