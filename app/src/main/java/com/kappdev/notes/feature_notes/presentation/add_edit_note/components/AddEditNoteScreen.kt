package com.kappdev.notes.feature_notes.presentation.add_edit_note.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.BackgroundImage
import com.kappdev.notes.core.presentation.components.TransparentTextField
import com.kappdev.notes.core.presentation.navigation.componets.SetupNavGraph
import com.kappdev.notes.feature_notes.presentation.add_edit_note.AddEditNoteViewModel

@ExperimentalMaterialApi
@Composable
fun AddEditNoteScreen(
    navController: NavHostController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
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
            ScreenContent(viewModel)
        }
    }
}

@Composable
private fun ScreenContent(viewModel: AddEditNoteViewModel) {
    val noteTitle = viewModel.noteTitle.value
    val noteContent = viewModel.noteContent.value
    
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(4.dp),
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.16f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransparentTextField(
                text = noteTitle,
                hint = stringResource(R.string.hint_note_title),
                textFieldModifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                ),
                onValueChange = {
                    viewModel.setTitle(it)
                }
            )

            TransparentTextField(
                text = noteContent,
                textFieldModifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface
                ),
                onValueChange = {
                    viewModel.setContent(it)
                }
            )
        }
    }
}
