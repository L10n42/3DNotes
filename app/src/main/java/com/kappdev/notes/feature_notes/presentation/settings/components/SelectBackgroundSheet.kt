package com.kappdev.notes.feature_notes.presentation.settings.components

import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.kappdev.notes.R
import com.kappdev.notes.feature_notes.presentation.settings.SettingsViewModel
import com.kappdev.notes.feature_notes.presentation.util.components.BottomSheetTitleHat
import com.kappdev.notes.ui.custom_theme.CustomTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectBackgroundSheet(
    settingsViewModel: SettingsViewModel,
    sheetState: ModalBottomSheetState,
) {
    val imagesUris = settingsViewModel.imagesUris
    val animatedShape by animateDpAsState(
        targetValue = if (sheetState.offset.value in 0f..10f) 0.dp else 16.dp,
        animationSpec = tween(durationMillis = 50)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CustomTheme.colors.surface,
                shape = RoundedCornerShape(topStart = animatedShape, topEnd = animatedShape)
            )
    ) {
        BottomSheetTitleHat(R.string.select_background_title)

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(vertical = CustomTheme.spaces.small)
        ) {
            items(imagesUris) { imageUri ->
               GridItem(imageUri) { image ->
                   settingsViewModel.setBackgroundImage(image)
               }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun GridItem(
    imageUri: Uri,
    onClick: (uri: Uri) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .padding(CustomTheme.spaces.extraSmall),
        shape = CustomTheme.shapes.medium,
        backgroundColor = Color.Transparent,
        onClick = {
            onClick(imageUri)
        }
    ) {
        SubcomposeAsyncImage(
            model = imageUri,
            contentDescription = null,
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = CustomTheme.colors.primary
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = CustomTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
    }
}