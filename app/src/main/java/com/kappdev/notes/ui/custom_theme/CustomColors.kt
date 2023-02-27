package com.kappdev.notes.ui.custom_theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class CustomColors(
    primary: Color,
    secondary: Color,
    surface: Color,
    background: Color,
    transparentSurface: Color = surface.copy(alpha = 0.12f),
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onSurface: Color,
    onBackground: Color,
    onError: Color,
    isLight: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var surface by mutableStateOf(surface)
        private set
    var background by mutableStateOf(background)
        private set
    var transparentSurface by mutableStateOf(transparentSurface)
        private set
    var error by mutableStateOf(error)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var onError by mutableStateOf(onError)
        private set
    var isLight by mutableStateOf(isLight)
        private set

    fun copy(
        primary: Color = this.primary,
        secondary: Color = this.secondary,
        surface: Color = this.surface,
        background: Color = this.background,
        transparentSurface: Color = this.transparentSurface,
        error: Color = this.error,
        onPrimary: Color = this.onPrimary,
        onSecondary: Color = this.onSecondary,
        onSurface: Color = this.onSurface,
        onBackground: Color = this.onBackground,
        onError: Color = this.onError,
        isLight: Boolean = this.isLight
    ) = CustomColors(
        primary,
        secondary,
        surface,
        background,
        transparentSurface,
        error,
        onPrimary,
        onSecondary,
        onSurface,
        onBackground,
        onError,
        isLight
    )

    fun updateColorsFrom(other: CustomColors) {
        primary = other.primary
        secondary = other.secondary
        surface = other.surface
        background = other.background
        transparentSurface = other.transparentSurface
        error = other.error
        onPrimary = other.onPrimary
        onSecondary = other.onSecondary
        onSurface = other.onSurface
        onBackground = other.onBackground
        onError = other.onError
        isLight = other.isLight
    }
}





















