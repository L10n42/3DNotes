package com.kappdev.notes.feature_notes.presentation.util.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.presentation.notes.NotesViewModel
import com.kappdev.notes.ui.custom_theme.CustomTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchDialog(
    viewModel: NotesViewModel
) {
    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }

    val animatedPadding by animateDpAsState(
        targetValue = if (isVisible) 0.dp else CustomTheme.spaces.small,
        animationSpec = tween(durationMillis = AnimDuration)
    )
    val animatedBarAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = AnimDuration)
    )
    val animatedCornerRadius by animateDpAsState(
        targetValue = if (isVisible) 0.dp else 16.dp,
        animationSpec = tween(durationMillis = AnimDuration)
    )
    val animatedBackgroundAlpha by animateFloatAsState(
        targetValue = if (isVisible) CustomTheme.opacity.backgroundOpacity else 0f,
        animationSpec = tween(durationMillis = AnimDuration)
    )

    val finish: () -> Unit = {
        scope.launch {
            isVisible = false
            delay(AnimDuration.toLong())
            viewModel.switchSearchModeOFF()
        }
    }

    LaunchedEffect(key1 = true) { isVisible = true }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = CustomTheme.colors.surface.copy(alpha = animatedBackgroundAlpha)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(CustomTheme.spaces.small),
            contentPadding = PaddingValues(bottom = CustomTheme.spaces.small),
            modifier = Modifier.fillMaxSize()
        ) {
            stickyHeader {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(animatedBarAlpha)
                        .padding(
                            top = animatedPadding,
                            start = animatedPadding,
                            end = animatedPadding
                        ),
                    shape = RoundedCornerShape(animatedCornerRadius),
                    onCancel = finish
                ) {
                    viewModel.search(it)
                }
            }
        }
    }
}

private const val AnimDuration = 200