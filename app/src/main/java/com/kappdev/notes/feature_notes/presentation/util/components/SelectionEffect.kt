package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kappdev.notes.ui.custom_theme.CustomTheme

enum class SelectionCardType { FOLDER, NOTE }

@Composable
fun SelectionEffect(
    isSelected: Boolean,
    shape: Shape = CustomTheme.shapes.large,
    cardType: SelectionCardType,
    height: Dp,
    width: Dp
) {
    AnimatedVisibility(
        visible = isSelected,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .height(height)
                .width(width)
                .background(
                    color = CustomTheme.colors.primary.copy(alpha = 0.1f), shape = shape
                )
                .border(
                    width = 2.dp,
                    color = CustomTheme.colors.primary.copy(alpha = 0.7f),
                    shape = shape
                )
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "check icon",
                tint = CustomTheme.colors.primary,
                modifier = Modifier
                    .align(
                        if (cardType == SelectionCardType.FOLDER) Alignment.CenterEnd else Alignment.TopEnd
                    )
                    .padding(
                        end = CustomTheme.spaces.medium,
                        top = if (cardType == SelectionCardType.NOTE) CustomTheme.spaces.medium else 0.dp
                    )
            )
        }
    }
}