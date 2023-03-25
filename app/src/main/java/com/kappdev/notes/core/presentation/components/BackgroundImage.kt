package com.kappdev.notes.core.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.kappdev.notes.feature_notes.domain.model.ImageShade
import com.kappdev.notes.feature_notes.domain.util.ShadeColor

@Composable
fun BackgroundImage(
    image: Bitmap,
    imageShade: ImageShade,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val tint = when(imageShade.color) {
        ShadeColor.Black -> Color.Black.copy(imageShade.opacity)
        ShadeColor.White -> Color.White.copy(imageShade.opacity)
    }

    Box(
        modifier = modifier
    ) {
        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = "screen background image",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(tint, BlendMode.Color),
            modifier = Modifier.fillMaxSize()
        )

        content()
    }
}