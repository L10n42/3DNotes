package com.kappdev.notes.core.domain.repository

import android.graphics.Bitmap
import com.kappdev.notes.feature_notes.domain.model.ImageShade

interface SettingRepository {

    fun getBackgroundOpacity(): Float

    fun setBackgroundOpacity(value: Float)

    fun getTheme(): Boolean

    fun setTheme(isDark: Boolean)

    fun setBackgroundImage(image: Bitmap)

    fun getBackgroundImage(): Bitmap

    fun setImageShade(imageShade: ImageShade)

    fun getImageShade(): ImageShade

    fun setVisibleLines(value: Int)

    fun getVisibleLines(): Int
}