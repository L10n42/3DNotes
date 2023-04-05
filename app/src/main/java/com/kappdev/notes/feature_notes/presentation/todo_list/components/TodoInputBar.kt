package com.kappdev.notes.feature_notes.presentation.todo_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.presentation.todo_list.TodoListViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun TodoInputBar(
    viewModel: TodoListViewModel
) {
    val value = viewModel.currentValue.value
    val valueIsNotEmpty = value.text.trim().isNotBlank()

    val done = {
        if (viewModel.editingTodo != null) viewModel.saveEdit() else viewModel.addTodo()
    }

    TextField(
        value = value,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = CustomTheme.colors.onBottomBarColor,
            unfocusedLabelColor = CustomTheme.colors.onBottomBarColor.copy(0.6f),
            focusedLabelColor = CustomTheme.colors.onBottomBarColor.copy(0.6f),
            backgroundColor = CustomTheme.colors.bottomBarColor,
            cursorColor = CustomTheme.colors.primary,
            focusedIndicatorColor = CustomTheme.colors.primary,
            unfocusedIndicatorColor = CustomTheme.colors.onBottomBarColor.copy(0.6f)
        ),
        trailingIcon = {
            IconButton(
                enabled = valueIsNotEmpty,
                onClick = done
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "done",
                    tint = if (valueIsNotEmpty) {
                        CustomTheme.colors.onBottomBarColor
                    } else {
                        CustomTheme.colors.onBottomBarColor.copy(0.6f)
                    }
                )
            }
        },
        onValueChange = { newText ->
            viewModel.setCurrentValue(newText)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.hint_text),
                color = CustomTheme.colors.onBottomBarColor.copy(0.6f)
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 16.sp
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Sentences
        ),
        keyboardActions = KeyboardActions(
            onDone = { if (valueIsNotEmpty) done() }
        ),
        modifier = Modifier.fillMaxWidth()
    )
}