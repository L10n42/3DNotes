package com.kappdev.notes.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import com.kappdev.notes.ui.theme.*

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    text: String,
    hint: String = "",
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
) {
    var textFieldMaxX by remember { mutableStateOf(0f) }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            textStyle = textStyle,
            modifier = textFieldModifier.onGloballyPositioned { coordinates ->
                val rect = coordinates.boundsInParent()
                textFieldMaxX = rect.right
            },
            cursorBrush = Brush.linearGradient(
                colors = CursorGradient,
                start = Offset.Zero,
                end = Offset(textFieldMaxX, 0f)
            )
        )

        if (text.isBlank()) {
            Text(
                text = hint,
                style = textStyle,
                color = Black_60A
            )
        }
    }
}