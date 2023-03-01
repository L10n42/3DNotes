package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.ButtonsCouple
import com.kappdev.notes.core.presentation.components.CustomTextField
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun NewFolderBS(
    viewModel: NotesViewModel,
    closeBS: () -> Unit
) {
    var folderName by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CustomTheme.colors.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(all = 16.dp)
    ) {
        NewFolderTitle()

        CustomTextField(
            text = folderName,
            hint = stringResource(R.string.hint_name),
            onTextChanged = { folderName = it },
            modifier = Modifier.fillMaxWidth()
        )

        ButtonsCouple(
            modifier = Modifier.fillMaxWidth(),
            positiveBtnEnable = folderName.trim().isNotBlank(),
            positiveTitleResId = R.string.btn_ok,
            negativeTitleResId = R.string.btn_cancel,
            onNegativeClick = closeBS,
            onPositiveClick = {
                viewModel.createFolder(folderName)
                closeBS()
            }
        )
    }
}

@Composable
private fun NewFolderTitle() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.title_new_folder),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}