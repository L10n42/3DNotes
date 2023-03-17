package com.kappdev.notes.feature_notes.presentation.todo_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleEventObserver
import com.kappdev.notes.feature_notes.presentation.todo_list.TodoListViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun TodoListContent(viewModel: TodoListViewModel) {
    val listState = rememberLazyListState()
    val todoListName = viewModel.todoListName.value
    val todoList = viewModel.todoList

    LaunchedEffect(key1 = todoList.size) {
        if (todoList.isNotEmpty()) listState.animateScrollToItem(todoList.lastIndex)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_STOP && viewModel.noteIsNotBlank() && viewModel.noteChanged()) {
//                viewModel.save()
//            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LazyColumn(
        state = listState,
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

