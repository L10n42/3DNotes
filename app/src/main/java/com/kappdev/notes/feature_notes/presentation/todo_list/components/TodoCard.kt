package com.kappdev.notes.feature_notes.presentation.todo_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.domain.model.Todo
import com.kappdev.notes.feature_notes.presentation.todo_list.TodoListViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.MorePopupBtn
import com.kappdev.notes.ui.custom_theme.CustomTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoCard(
    todo: Todo,
    viewModel: TodoListViewModel
) {
    Card(
        elevation = 0.dp,
        shape = CustomTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = CustomTheme.colors.transparentSurface,
        onClick = { viewModel.editTodo(todo) }
    ) {
        Row(
            modifier = Modifier.padding(vertical = CustomTheme.spaces.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.checked,
                onCheckedChange = { isChecked ->
                    viewModel.checkedChange(todo, isChecked)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = CustomTheme.colors.onBackground,
                    uncheckedColor = CustomTheme.colors.onSurface,
                    checkmarkColor = CustomTheme.colors.onSurface
                )
            )

            Text(todo.text, todo.checked, modifier = Modifier.weight(1f))

            MorePopupBtn(
                titlesResIds = listOf(R.string.title_remove),
                topEndPaddingValue = 8.dp,
                onItemClick = { id ->
                    when(id) {
                        R.string.title_remove -> viewModel.removeTodo(todo)
                    }
                }
            )
        }
    }
}

@Composable
private fun Text(
    text: String,
    checked: Boolean,
    modifier: Modifier = Modifier
) {
    val checkedStyle = SpanStyle(
        textDecoration = TextDecoration.LineThrough,
        color = CustomTheme.colors.onBackground
    )
    val newText = buildAnnotatedString {
        if (checked) {
            withStyle(checkedStyle) { append(text) }
        } else append(text)
    }
    Text(
        text = newText,
        fontSize = 18.sp,
        color = CustomTheme.colors.onSurface,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}