package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    background: Color = CustomTheme.colors.surface,
    shape: Shape = CustomTheme.shapes.large,
    onCancel: () -> Unit,
    onSearch: (value: String) -> Unit
) {
    var value by remember { mutableStateOf("") }

    TextField(
        value = value,
        singleLine = true,
        shape = shape,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search_icon",
                tint = CustomTheme.colors.onSurface
            )
        },
        trailingIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ClearButton(isVisible = value.isNotBlank()) {
                    value = ""
                    onSearch(value)
                }
                CancelButton(
                    modifier = Modifier.padding(end = CustomTheme.spaces.extraSmall),
                    onClick = onCancel
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(R.string.hint_search),
                color = CustomTheme.colors.onSurface,
                fontSize = 18.sp
            )
        },
        onValueChange = { newValue ->
            value = newValue
            onSearch(newValue)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = background,
            cursorColor = CustomTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = TextStyle(
            color = CustomTheme.colors.onSurface,
            fontSize = 18.sp
        ),
        modifier = modifier
    )
}

@Composable
private fun ClearButton(
    isVisible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        IconButton(
            onClick = onClick,
        ) {
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "clear search button",
                tint = CustomTheme.colors.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun CancelButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.btn_cancel),
            color = CustomTheme.colors.primary,
            fontSize = 16.sp,
        )
    }
}
















