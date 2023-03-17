package com.kappdev.notes.feature_notes.presentation.todo_list.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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
        onClick = {
            viewModel.editTodo(todo)
        },
        shape = CustomTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = CustomTheme.colors.transparentSurface
    ) {
        Row(
            modifier = Modifier.padding(vertical = CustomTheme.spaces.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = todo.checked,
                onCheckedChange = {
                    val newTodo = todo.copy(checked = it)
                    viewModel.updateTodo(newTodo)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = CustomTheme.colors.onBackground,
                    uncheckedColor = CustomTheme.colors.onSurface,
                    checkmarkColor = CustomTheme.colors.onSurface
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val configuration = LocalConfiguration.current
                val isLandscapeMode = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                val textFraction = if (isLandscapeMode) 0.95f else 0.8f

                Text(todo.text, todo.checked, modifier = Modifier.fillMaxWidth(textFraction))

                MorePopupBtn(
                    titlesResIds = listOf(R.string.title_remove),
                    onItemClick = { id ->
                        when(id) {
                            R.string.title_remove -> viewModel.removeTodo(todo)
                        }
                    }
                )
            }
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