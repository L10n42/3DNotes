package com.kappdev.notes.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

@Composable
fun BackgroundImage(
    image: Painter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = image,
            contentDescription = "screen background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        content()
    }
}