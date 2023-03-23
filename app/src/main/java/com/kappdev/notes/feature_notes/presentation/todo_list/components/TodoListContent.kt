package com.kappdev.notes.feature_notes.presentation.todo_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.kappdev.notes.feature_notes.presentation.todo_list.TodoListViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun TodoListContent(viewModel: TodoListViewModel) {
    val todoList = viewModel.todoList.reversed()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP && viewModel.noteIsNotBlank()) {
                viewModel.saveTodoList()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(CustomTheme.spaces.small),
        contentPadding = PaddingValues(
            start = CustomTheme.spaces.small,
            end = CustomTheme.spaces.small,
            top = CustomTheme.spaces.medium,
            bottom = 56.dp + CustomTheme.spaces.small
        ),
        modifier = Modifier.fillMaxSize()
    ) {
        items(todoList) { todo ->
            TodoCard(todo, viewModel)
        }
    }
}

