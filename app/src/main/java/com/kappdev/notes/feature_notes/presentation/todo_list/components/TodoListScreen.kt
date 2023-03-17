package com.kappdev.notes.feature_notes.presentation.todo_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.notes.feature_notes.presentation.todo_list.TodoListViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.SelectFolderBottomSheet
import com.kappdev.notes.ui.custom_theme.CustomTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoListScreen(
    todoListId: Long,
    folderId: Long?,
    navController: NavHostController,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()
    val navigate = viewModel.navigate.value
    val openBottomSheet = viewModel.openBottomSheet.value

    LaunchedEffect(key1 = openBottomSheet) {
        if (openBottomSheet) {
            sheetState.show()
            viewModel.closeBottomSheet()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.setEditTodoListId(todoListId)
        if (folderId != null && folderId > 0) viewModel.setFolderId(folderId)
        if (todoListId > 0) viewModel.getTodoListById()
    }

    LaunchedEffect(key1 = navigate) {
        navigate?.let { route ->
            navController.navigate(route) { popUpTo(0) }
            viewModel.clearNavigateRoute()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = CustomTheme.colors.onSurface.copy(alpha = CustomTheme.opacity.scrimOpacity),
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            SelectFolderBottomSheet(folders = viewModel.allFolders, sheetState) { folderId ->
                viewModel.moveTo(folderId)
                scope.launch { sheetState.hide() }
            }
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            topBar = {
                //AddEditNoteTopBar(viewModel)
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                TodoListContent(viewModel)

                TodoInputBar(viewModel = viewModel)
            }
        }
    }
}