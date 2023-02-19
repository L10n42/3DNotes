package com.kappdev.notes.core.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    isError: Boolean = false,
    onTextChanged: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val animCorner by animateDpAsState(
        targetValue = if (isFocused) 0.dp else DefaultShapeDp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    val dynamicShape = RoundedCornerShape(
        topStart = DefaultShapeDp, topEnd = DefaultShapeDp, bottomStart = animCorner, bottomEnd = animCorner
    )

    TextField(
        value = text,
        isError = isError,
        singleLine = singleLine,
        enabled = enabled,
        shape = dynamicShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            unfocusedLabelColor = MaterialTheme.colors.onBackground,
            focusedLabelColor = MaterialTheme.colors.onBackground,
            backgroundColor = MaterialTheme.colors.background,
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            if (isFocused) {
                IconButton(
                    onClick = { onTextChanged("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "cancel_icon",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        },
        onValueChange = { newText ->
            onTextChanged(newText)
        },
        placeholder = {
            Text(text = hint, color = MaterialTheme.colors.onBackground)
        },
        textStyle = TextStyle(fontSize = 18.sp),
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { state ->
                isFocused = state.isFocused
            }
    )
}

private val DefaultShapeDp = 8.dp