package com.kappdev.notes.feature_notes.presentation.notes.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.notes.NotesBottomSheet
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.util.SubButton
import com.kappdev.notes.feature_notes.presentation.util.components.AnimatedMultiAddButton
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun NotesScreen(
    navController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()
    val navigate = viewModel.navigate.value

    LaunchedEffect(key1 = true) {
        viewModel.getAllData()
    }

    LaunchedEffect(key1 = navigate) {
        navigate?.let { route ->
            navController.navigate(route)
            viewModel.clearNavigateRoute()
        }
    }

    var currentBottomSheet by remember { mutableStateOf<NotesBottomSheet?>(null) }
    val closeSheet: () -> Unit = {
        scope.launch { sheetState.hide() }
    }
    val openSheet = { bottomSheet: NotesBottomSheet ->
        currentBottomSheet = bottomSheet
        scope.launch { sheetState.show() }
    }

    if(!sheetState.isVisible) currentBottomSheet = null
    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.16f),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            if (currentBottomSheet != null) {
                NotesBottomSheetController(
                    viewModel = viewModel,
                    currentSheet = currentBottomSheet!!,
                    closeBS = closeSheet
                )
            } else Box(Modifier.height(1.dp))
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            topBar = {
//                NotesTopBar(
//                    goToSettings = { navController.navigate(Screen.Settings.route) }
//                )
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                NotesContent(viewModel)

                AnimatedMultiAddButton(
                    buttons = listOf(SubButton.NotesFolder, SubButton.ToDoList, SubButton.NoteText)
                ) { buttonId ->
                    when(buttonId) {
                        SubButton.NoteText.id -> viewModel.navigate(Screen.AddEditNote.route)
                        SubButton.ToDoList.id -> Log.d("onClick", "new todo list btn was clicked!")
                        SubButton.NotesFolder.id -> openSheet(NotesBottomSheet.NewFolder)
                    }
                }
            }
        }
    }
}