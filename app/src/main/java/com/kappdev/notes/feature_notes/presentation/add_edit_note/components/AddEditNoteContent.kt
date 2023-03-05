package com.kappdev.notes.feature_notes.presentation.add_edit_note.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.TransparentTextField
import com.kappdev.notes.feature_notes.presentation.add_edit_note.AddEditNoteViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun AddEditNoteContent(viewModel: AddEditNoteViewModel) {
    val noteTitle = viewModel.noteTitle.value
    val noteContent = viewModel.noteContent.value

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP && viewModel.noteIsNotBlank() && viewModel.noteChanged()) {
                viewModel.save()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = CustomTheme.spaces.medium),
        elevation = 0.dp,
        shape = CustomTheme.shapes.medium,
        backgroundColor = CustomTheme.colors.transparentSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransparentTextField(
                value = noteTitle,
                hint = stringResource(R.string.hint_note_title),
                textFieldModifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = CustomTheme.colors.onSurface
                ),
                onValueChange = {
                    viewModel.setTitle(it)
                }
            )

            TransparentTextField(
                value = noteContent,
                textFieldModifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = CustomTheme.colors.onSurface
                ),
                onValueChange = {
                    viewModel.setContent(it)
                }
            )
        }
    }
}