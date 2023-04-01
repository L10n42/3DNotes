package com.kappdev.notes.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import com.kappdev.notes.ui.custom_theme.CustomTheme
import com.kappdev.notes.ui.theme.CursorGradient

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    value: TextFieldValue,
    hint: String = "",
    singleLine: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit,
    textStyle: TextStyle = TextStyle(),
) {
    var textFieldMaxX by remember { mutableStateOf(0f) }

    Box(modifier = modifier) {
        BasicTextField(
            value = value,
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
            ),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )

        if (value.text.isBlank()) {
            Text(
                text = hint,
                style = textStyle,
                color = CustomTheme.colors.onBackground
            )
        }
    }
}