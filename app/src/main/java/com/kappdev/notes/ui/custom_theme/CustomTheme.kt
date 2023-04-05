package com.kappdev.notes.ui.custom_theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.kappdev.notes.ui.theme.*

@Composable
fun CustomNotesTheme(
    spaces: CustomSpaces = CustomTheme.spaces,
    typography: CustomTypography = CustomTheme.typography,
    shapes: CustomShapes = CustomTheme.shapes,
    systemUiColors: SystemUiColors = SystemUiColors(),
    opacity: CustomOpacity = CustomOpacity(),
    lightColors: CustomColors = lightColors(),
    darkColors: CustomColors = darkColors(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val currentColor = (if (darkTheme) darkColors else lightColors)
    val finalColor = currentColor.copy(
        transparentSurface = currentColor.surface.copy(alpha = opacity.backgroundOpacity),
        topBarColor = systemUiColors.statusBarColor,
        bottomBarColor = systemUiColors.navigationBarColor,
        onTopBarColor = if (systemUiColors.isStatusBarIconsLight) Color.White else Color.Black,
        onBottomBarColor = if (systemUiColors.isNavigationBarIconsLight) Color.White else Color.Black
    )
    val rememberedColors = remember { finalColor.copy() }.apply { updateColorsFrom(finalColor) }
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalSpaces provides spaces,
        LocalShapes provides shapes,
        LocalTypography provides typography,
        LocalOpacity provides opacity
    ) {
        ProvideTextStyle(value = typography.body, content = content)
    }
}

object CustomTheme {
    val colors: CustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val spaces: CustomSpaces
        @Composable
        @ReadOnlyComposable
        get() = LocalSpaces.current

    val typography: CustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: CustomShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val opacity: CustomOpacity
        @Composable
        @ReadOnlyComposable
        get() = LocalOpacity.current
}

val LocalOpacity = staticCompositionLocalOf { CustomOpacity() }
val LocalColors = staticCompositionLocalOf { lightColors() }
val LocalShapes = staticCompositionLocalOf { CustomShapes() }
val LocalSpaces = staticCompositionLocalOf { CustomSpaces() }
val LocalTypography = staticCompositionLocalOf { CustomTypography() }

private fun darkColors() = CustomColors(
    primary = PrimaryBlue,
    secondary = Teal200,
    background = BackgroundGray,
    surface = SurfaceGray,
    error = LightRed,
    topBarColor = SurfaceGray,
    bottomBarColor = BackgroundGray,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = White_60A,
    onSurface = White_87A,
    onError = Color.Black,
    isLight = false,
    onTopBarColor = White_87A,
    onBottomBarColor = White_60A
    // transparentSurface = surface.copy(alpha = 0.12f)
)

private fun lightColors() = CustomColors(
    primary = PrimaryBlue,
    secondary = Teal200,
    background = BackgroundWhite,
    surface = Color.White,
    error = ErrorRed,
    topBarColor = Color.White,
    bottomBarColor = BackgroundWhite,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Black_60A,
    onSurface = Black_87A,
    onError = Color.White,
    isLight = true,
    onTopBarColor = Black_87A,
    onBottomBarColor = Black_60A
    // transparentSurface = surface.copy(alpha = 0.12f)
)