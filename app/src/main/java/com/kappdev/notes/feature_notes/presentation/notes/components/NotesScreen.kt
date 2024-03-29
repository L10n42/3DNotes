package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.notes.NotesBottomSheet
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.notes.SearchViewModel
import com.kappdev.notes.feature_notes.presentation.util.SubButton
import com.kappdev.notes.feature_notes.presentation.util.components.AnimatedMultiAddButton
import com.kappdev.notes.feature_notes.presentation.util.components.SearchDialog
import com.kappdev.notes.ui.custom_theme.CustomTheme

@ExperimentalMaterialApi
@Composable
fun NotesScreen(
    navController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()
    val navigate = viewModel.navigate.value
    val selectionModeON = viewModel.selectionMode.value
    val searchModeON = viewModel.searchMode.value
    val currentBottomSheet = viewModel.currentBottomSheet.value

    LaunchedEffect(key1 = true) { viewModel.getAllData() }

    LaunchedEffect(key1 = currentBottomSheet) {
        if (currentBottomSheet != null) sheetState.show() else sheetState.hide()
    }

    LaunchedEffect(key1 = navigate) {
        navigate?.let { route ->
            navController.navigate(route)
            viewModel.clearNavigateRoute()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = CustomTheme.colors.onSurface.copy(alpha = CustomTheme.opacity.scrimOpacity),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            if (currentBottomSheet != null) {
                NotesBottomSheetController(
                    viewModel = viewModel,
                    currentSheet = currentBottomSheet,
                    sheetState = sheetState
                )
            } else Box(Modifier.height(1.dp))
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            topBar = {
                NoteSelectionTopBar(
                    isVisible = selectionModeON && !searchModeON,
                    viewModel = viewModel
                )
            },
            bottomBar = {
                NoteSelectionBottomBar(
                    isVisible = selectionModeON && !searchModeON,
                    viewModel = viewModel
                )
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                NotesContent(viewModel, searchViewModel)

                if (searchModeON) SearchDialog(viewModel, searchViewModel)

                AnimatedMultiAddButton(
                    isVisible = !searchModeON && !selectionModeON,
                    buttons = listOf(SubButton.NotesFolder, SubButton.ToDoList, SubButton.NoteText)
                ) { buttonId ->
                    when(buttonId) {
                        SubButton.NoteText.id -> viewModel.navigate(Screen.AddEditNote.route)
                        SubButton.ToDoList.id -> viewModel.navigate(Screen.TodoList.route)
                        SubButton.NotesFolder.id -> viewModel.openSheet(NotesBottomSheet.NewFolder)
                    }
                }
            }
        }
    }
}