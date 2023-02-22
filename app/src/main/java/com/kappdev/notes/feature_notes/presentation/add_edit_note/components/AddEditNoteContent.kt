package com.kappdev.notes.feature_notes.presentation.add_edit_note.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun AddEditNoteContent(viewModel: AddEditNoteViewModel) {
    val context = LocalContext.current
    val noteTitle = viewModel.noteTitle.value
    val noteContent = viewModel.noteContent.value

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP && viewModel.noteIsNotBlank() && viewModel.noteChanged()) {
                viewModel.save(
                    onSuccess = { Toast.makeText(context, R.string.msg_saved, Toast.LENGTH_SHORT).show() },
                    onFailure = { Toast.makeText(context, R.string.msg_could_not_save, Toast.LENGTH_SHORT).show() }
                )
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
        ,
        elevation = 0.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.12f)
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
                textFieldModifier = Modifier.fillMaxSize(),
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