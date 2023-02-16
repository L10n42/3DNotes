package com.kappdev.notes.feature_notes.presentation.notes.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.feature_notes.presentation.util.SubButtons
import com.kappdev.notes.feature_notes.presentation.util.components.AnimatedMultiAddButton
import com.kappdev.notes.feature_notes.presentation.util.components.AnimatedMultiAddButtonColors

@ExperimentalMaterialApi
@Composable
fun NotesScreen(
    navController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scaffoldState = rememberScaffoldState()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.16f),
        sheetElevation = 0.dp,
        sheetContent = { Box(Modifier.height(1.dp)) }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                NotesContent(viewModel)

                AnimatedMultiAddButton { buttonId ->
                    when(buttonId) {
                        SubButtons.NoteText.id -> navController.navigate(Screen.AddEditNote.route)
                        SubButtons.ToDoList.id -> Log.d("onClick", "new todo list btn was clicked!")
                        SubButtons.NotesFolder.id -> Log.d("onClick", "new folder btn was clicked!")
                    }
                }
            }
        }
    }
}