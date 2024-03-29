package com.kappdev.notes.core.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.chargemap.compose.numberpicker.NumberPicker
import com.kappdev.notes.ui.custom_theme.CustomTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PopupNumberPicker(
    expanded: Boolean,
    range: Iterable<Int>,
    selectedValue: Int,
    modifier: Modifier = Modifier,
    elevation: Dp = 16.dp,
    offset: IntOffset = IntOffset(0, 0),
    border: BorderStroke? = null,
    alignment: Alignment = Alignment.Center,
    backgroundColor: Color = CustomTheme.colors.surface,
    dividersColor: Color = CustomTheme.colors.onSurface.copy(0.5f),
    shape: Shape = RoundedCornerShape(16.dp),
    textStyle: TextStyle = TextStyle(color = CustomTheme.colors.onSurface),
    onValueChange: (value: Int) -> Unit,
    dismiss: () -> Unit,
) {
    val expandedState = remember { MutableTransitionState(false) }
    expandedState.targetState = expanded

    if (expandedState.currentState || expandedState.targetState || !expandedState.isIdle) {
        Popup(
            onDismissRequest = dismiss,
            alignment = alignment,
            offset = offset,
            properties = PopupProperties(focusable = true)
        ) {
            AnimatedVisibility(
                visibleState = expandedState,
                enter = scaleIn(
                    initialScale = 0.5f,
                    transformOrigin = TransformOrigin(0.5f, 0.5f)
                ) + fadeIn(),
                exit = scaleOut(
                    targetScale = 0.5f,
                    transformOrigin = TransformOrigin(0.5f, 0.5f)
                ) + fadeOut()
            ) {
                Surface(
                    shape = shape,
                    color = backgroundColor,
                    elevation = elevation,
                    border = border,
                    modifier = modifier,
                ) {
                    NumberPicker(
                        range = range,
                        value = selectedValue,
                        dividersColor = dividersColor,
                        onValueChange = onValueChange,
                        textStyle = textStyle,
                        modifier = Modifier.width(65.dp)
                    )
                }
            }
        }
    }
}