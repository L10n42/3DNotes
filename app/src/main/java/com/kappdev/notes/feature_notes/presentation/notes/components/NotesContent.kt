package com.kappdev.notes.feature_notes.presentation.notes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kappdev.notes.MainActivity
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.components.BackgroundImage
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel

@Composable
fun NotesContent(
    viewModel: NotesViewModel
) {
    val backgroundImage = painterResource(id = R.drawable.background_image_1)

    BackgroundImage(
        image = backgroundImage,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hello New Project!",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}