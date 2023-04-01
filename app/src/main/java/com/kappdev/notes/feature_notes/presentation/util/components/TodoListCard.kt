package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.kappdev.notes.feature_notes.domain.model.AnnotatedTodoList
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.ui.custom_theme.CustomTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnnotatedTodoListCard(
    annotatedTodoList: AnnotatedTodoList,
    visibleLines: Int,
    modifier: Modifier = Modifier,
    onClick: (id: Long) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 0.dp,
        backgroundColor = CustomTheme.colors.transparentSurface,
        onClick = { onClick(annotatedTodoList.id) }
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Title(annotatedTodoList.name)

            val reversedList = annotatedTodoList.content.reversed()

            reversedList.forEachIndexed { index, todo ->
                if (index in 0..reversedList.lastIndex && index < visibleLines) {
                    TodoContentLine(text = todo.text, checked = todo.checked)
                } else return@forEachIndexed
            }

            Time(annotatedTodoList.timestamp)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoListCard(
    todoList: TodoList,
    modifier: Modifier = Modifier,
    visibleLines: Int,
    selected: Boolean = false,
    onLongClick: () -> Unit = {},
    onClick: (id: Long) -> Unit
) {
    var cardSize by remember { mutableStateOf(Size.Zero) }
    val cardWidth = with(LocalDensity.current) { cardSize.width.toDp() }
    val cardHeight = with(LocalDensity.current) { cardSize.height.toDp() }

    Box {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clip(CustomTheme.shapes.large)
                .combinedClickable(
                    onClick = { onClick(todoList.id) },
                    onLongClick = { onLongClick() }
                )
                .onGloballyPositioned { coordinates ->
                    cardSize = coordinates.size.toSize()
                },
            elevation = 0.dp,
            backgroundColor =  CustomTheme.colors.transparentSurface
        ) {
            Column(
                modifier = Modifier.padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Title(todoList.name, selected)

                val reversedList = todoList.content.reversed()

                reversedList.forEachIndexed { index, todo ->
                    if (index in 0..reversedList.lastIndex && index < visibleLines) {
                        TodoContentLine(text = todo.text, checked = todo.checked)
                    } else return@forEachIndexed
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Time(todoList.timestamp)

                    todoList.alarm?.let { alarmTime ->
                        AlarmTime(alarmTime)
                    }
                }
            }
        }

        SelectionEffect(isSelected = selected, height = cardHeight, width = cardWidth, cardType = SelectionCardType.NOTE)
    }
}

@Composable
private fun TodoContentLine(
    text: AnnotatedString,
    checked: Boolean
) {
    val checkedStyle = SpanStyle(
        textDecoration = TextDecoration.LineThrough
    )
    val newText = buildAnnotatedString {
        if (checked) {
            withStyle(checkedStyle) { append(text) }
        } else append(text)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Circle,
            contentDescription = "todo point",
            tint = CustomTheme.colors.onSurface,
            modifier = Modifier.size(8.dp)
        )

        Spacer(modifier = Modifier.width(CustomTheme.spaces.small))

        Text(
            text = newText,
            fontSize = 16.sp,
            color = CustomTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TodoContentLine(
    text: String,
    checked: Boolean
) {
    val checkedStyle = SpanStyle(
        textDecoration = TextDecoration.LineThrough
    )
    val newText = buildAnnotatedString {
        if (checked) {
            withStyle(checkedStyle) { append(text) }
        } else append(text)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Circle,
            contentDescription = "todo point",
            tint = CustomTheme.colors.onSurface,
            modifier = Modifier.size(8.dp)
        )

        Spacer(modifier = Modifier.width(CustomTheme.spaces.small))

        Text(
            text = newText,
            fontSize = 16.sp,
            color = CustomTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}