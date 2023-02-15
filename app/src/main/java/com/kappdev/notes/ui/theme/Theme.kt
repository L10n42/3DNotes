package com.kappdev.notes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = PrimaryBlue,
    primaryVariant = Purple700,
    secondary = Teal200,
    secondaryVariant = Teal200,
    background = BackgroundGray,
    surface = SurfaceGray,
    error = LightRed,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = White_60A,
    onSurface = White_87A,
    onError = Color.Black
)

private val LightColorPalette = lightColors(
    primary = PrimaryBlue,
    primaryVariant = Purple700,
    secondary = Teal200,
    secondaryVariant = DarkTeal,
    background = BackgroundWhite,
    surface = Color.White,
    error = ErrorRed,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Black_60A,
    onSurface = Black_87A,
    onError = Color.White
)

@Composable
fun NotesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}