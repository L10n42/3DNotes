package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.ButtonsCouple
import com.kappdev.notes.core.presentation.components.CustomTextField
import com.kappdev.notes.ui.custom_theme.CustomTheme

@Composable
fun AddEditFolderSheet(
    initValue: String = "",
    close: () -> Unit,
    onDone: (name: String) -> Unit
) {
    var folderName by remember { mutableStateOf(initValue) }
    val focusManager = LocalFocusManager.current

    val cancelWork = {
        focusManager.clearFocus()
        close()
    }

    val finishWork = {
        onDone(folderName)
        focusManager.clearFocus()
        close()
    }

    LaunchedEffect(key1 = initValue) { folderName = initValue }

    Column(
        verticalArrangement = Arrangement.spacedBy(CustomTheme.spaces.large),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CustomTheme.colors.background,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(all = 16.dp)
    ) {
        NewFolderTitle(
            if (initValue.isBlank()) R.string.title_new_folder else R.string.title_edit_folder
        )

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
            onNegativeClick = cancelWork,
            onPositiveClick = finishWork
        )
    }
}

@Composable
private fun NewFolderTitle(titleRes: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(titleRes),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = CustomTheme.colors.onSurface
        )
    }
}