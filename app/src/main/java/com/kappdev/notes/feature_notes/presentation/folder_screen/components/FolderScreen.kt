package com.kappdev.notes.feature_notes.presentation.folder_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.presentation.folder_screen.FolderViewModel
import com.kappdev.notes.feature_notes.presentation.notes.components.AddEditFolderSheet
import com.kappdev.notes.feature_notes.presentation.util.SubButton
import com.kappdev.notes.feature_notes.presentation.util.components.AnimatedMultiAddButton
import com.kappdev.notes.feature_notes.presentation.util.components.NoteCard
import com.kappdev.notes.feature_notes.presentation.util.components.TodoListCard
import com.kappdev.notes.ui.custom_theme.CustomTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FolderScreen(
    folderId: Long,
    navController: NavHostController,
    viewModel: FolderViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()
    val navigate = viewModel.navigate.value
    val currentFolder = viewModel.currentFolder.value
    val openBottomSheet = viewModel.openBottomSheet.value

    val closeSheet: () -> Unit = { scope.launch { sheetState.hide() } }
    val openSheet: () -> Unit = { scope.launch { sheetState.show() } }

    LaunchedEffect(key1 = openBottomSheet) {
        if (openBottomSheet) {
            openSheet()
            viewModel.closeBottomSheet()
        }
    }

    LaunchedEffect(key1 = true) {
        if (folderId > 0) viewModel.getContent(folderId) else navController.popBackStack()
    }

    LaunchedEffect(key1 = navigate) {
        navigate?.let { route ->
            navController.navigate(route) { popUpTo(0) }
            viewModel.clearNavigateRoute()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = CustomTheme.colors.onSurface.copy(alpha = 0.16f),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            AddEditFolderSheet(initValue = currentFolder.name, close = closeSheet) { newName ->
                viewModel.updateFolder(newName)
            }
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            topBar = {
                FolderScreenTopBar(title = currentFolder.name, viewModel)
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Content(viewModel)

                AnimatedMultiAddButton(
                    buttons = listOf(SubButton.ToDoList, SubButton.NoteText)
                ) { buttonId ->
                    when(buttonId) {
                        SubButton.NoteText.id -> navController.navigate(
                            Screen.AddEditNote.route.plus("?folderId=${viewModel.folderId}")
                        )
                        SubButton.ToDoList.id -> navController.navigate(
                            Screen.TodoList.route.plus("?folderId=${viewModel.folderId}")
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Content(viewModel: FolderViewModel) {
    val listState = rememberLazyListState()
    val content = viewModel.contentList

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(CustomTheme.spaces.small),
        contentPadding = PaddingValues(all = CustomTheme.spaces.small),
        modifier = Modifier.fillMaxSize()
    ) {
        items(content) { item ->
            when (item) {
                is Note -> NoteCard(item, visibleLines = viewModel.visibleLines) { id ->
                    viewModel.navigate(
                        Screen.AddEditNote.route.plus("?noteId=$id&folderId=${viewModel.folderId}")
                    )
                }
                is TodoList -> TodoListCard(todoList = item, visibleLines = viewModel.visibleLines) { id ->
                    viewModel.navigate(
                        Screen.TodoList.route.plus("?todoListId=$id&folderId=${viewModel.folderId}")
                    )
                }
            }
        }
    }
}