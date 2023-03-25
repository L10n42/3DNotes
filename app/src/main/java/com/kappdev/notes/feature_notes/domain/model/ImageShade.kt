package com.kappdev.notes.feature_notes.domain.model

import com.kappdev.notes.feature_notes.domain.util.ShadeColor

data class ImageShade(
    val color: ShadeColor = ShadeColor.Black,
    val opacity: Float = 0f
)
