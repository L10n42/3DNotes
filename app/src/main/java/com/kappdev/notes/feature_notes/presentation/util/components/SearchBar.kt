package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun SearchBar(
    search: (value: String) -> Unit,
    viewModel: NotesViewModel
) {
    var value by remember { mutableStateOf("") }

    TextField(
        value = value,
        singleLine = true,
        shape = CustomTheme.shapes.large,
        readOnly = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search_icon",
                tint = CustomTheme.colors.onSurface
            )
        },
        trailingIcon = {
            MorePopupBtn(
                titlesResIds = listOf(R.string.settings_label),
                onItemClick = { id ->
                    when(id) {
                        R.string.settings_label -> viewModel.navigate(Screen.Settings.route)
                    }
                }
            )
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
            search(newValue)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = CustomTheme.colors.transparentSurface,
            cursorColor = CustomTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = TextStyle(
            color = CustomTheme.colors.onSurface,
            fontSize = 18.sp
        ),
        modifier = Modifier.fillMaxWidth()
    )
}