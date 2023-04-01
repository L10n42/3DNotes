package com.kappdev.notes.feature_notes.presentation.todo_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.BackButton
import com.kappdev.notes.core.presentation.components.ConfirmDialog
import com.kappdev.notes.core.presentation.components.TransparentTextField
import com.kappdev.notes.feature_notes.presentation.todo_list.TodoListViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.MorePopupBtn
import com.kappdev.notes.feature_notes.presentation.util.components.PickDateAndTime
import com.kappdev.notes.ui.custom_theme.CustomTheme
import java.time.LocalDateTime

@Composable
fun TodoListTopBar(
    viewModel: TodoListViewModel
) {
    val todoListId = viewModel.currentTodoListId.value
    val todoListName = viewModel.todoListName.value

    var showRemoveDialog by remember { mutableStateOf(false) }
    if (showRemoveDialog) {
        ConfirmDialog(
            title = stringResource(R.string.title_remove),
            message = stringResource(R.string.confirm_remove_todo_list_msg),
            confirmText = stringResource(R.string.btn_remove),
            closeDialog = { showRemoveDialog = false },
            onConfirm = { viewModel.removeTodoListAndNavigateBack() }
        )
    }

    var showDateTimePicker by remember { mutableStateOf(false) }
    if (showDateTimePicker) {
        PickDateAndTime(
            initialTime = viewModel.alarmTime ?: LocalDateTime.now(),
            onCancel = { showDateTimePicker = false },
            onPicked = { viewModel.scheduleAlarmFor(it) }
        )
    }

    TopAppBar(
        title = {
            TransparentTextField(
                value = todoListName,
                singleLine = true,
                hint = stringResource(R.string.hint_todo_list_name),
                textFieldModifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = CustomTheme.colors.onSurface
                ),
                onValueChange = { newName ->
                    viewModel.setName(newName)
                }
            )
        },
        elevation = 0.dp,
        backgroundColor = CustomTheme.colors.transparentSurface,
        modifier = Modifier
            .padding(all = CustomTheme.spaces.small)
            .clip(CustomTheme.shapes.large),
        navigationIcon = {
            BackButton { viewModel.navigateBack() }
        },
        actions = {
            IconButton(
                enabled = viewModel.canSave(),
                onClick = {
                    viewModel.saveTodoList()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "done_btn",
                    tint = if (viewModel.canSave()) CustomTheme.colors.onSurface else CustomTheme.colors.onSurface.copy(alpha = 0.5f)
                )
            }

            if (todoListId > 0) {
                MorePopupBtn(
                    titlesResIds = listOf(R.string.title_remainder, R.string.title_move_to, R.string.title_share, R.string.title_remove),
                    onItemClick = { id ->
                        when(id) {
                            R.string.title_remove -> showRemoveDialog = true
                            R.string.title_move_to -> viewModel.openBottomSheet()
                            R.string.title_share -> viewModel.shareCurrentTodoList()
                            R.string.title_remainder -> showDateTimePicker = true
                        }
                    }
                )
            }
        }
    )
}