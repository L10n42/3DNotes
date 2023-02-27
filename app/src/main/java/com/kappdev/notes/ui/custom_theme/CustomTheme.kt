package com.kappdev.notes.ui.custom_theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.kappdev.notes.ui.theme.*

object CustomTheme {
    val colors: CustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}

val LocalColors = staticCompositionLocalOf { lightColors() }

private fun lightColors() = CustomColors(
    primary = PrimaryBlue,
    secondary = Teal200,
    background = BackgroundWhite,
    surface = Color.White,
    error = ErrorRed,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Black_60A,
    onSurface = Black_87A,
    onError = Color.White,
    isLight = true
    // transparentSurface = surface.copy(alpha = 0.12f)
)