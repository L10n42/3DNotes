package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kappdev.notes.ui.custom_theme.CustomTheme

enum class DividerPosition { TOP, BOTTOM, BOTH }

@Composable
fun CustomBar(
    height: Dp = 56.dp,
    background: Color = CustomTheme.colors.surface,
    dividerPosition: DividerPosition = DividerPosition.BOTTOM,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    content: @Composable RowScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(background),
    ) {
        if (dividerPosition == DividerPosition.TOP || dividerPosition == DividerPosition.BOTH) Divider()

        val contentRowHeight = if (dividerPosition == DividerPosition.BOTH) height - 2.dp else height - 1.dp
        Row(
            verticalAlignment = verticalAlignment,
            horizontalArrangement = horizontalArrangement,
            modifier = Modifier.fillMaxWidth().height(contentRowHeight),
            content = content
        )

        if (dividerPosition == DividerPosition.BOTTOM || dividerPosition == DividerPosition.BOTH) Divider()
    }
}