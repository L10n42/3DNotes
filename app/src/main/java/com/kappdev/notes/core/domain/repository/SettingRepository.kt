package com.kappdev.notes.core.domain.repository

import android.graphics.Bitmap

interface SettingRepository {

    fun getBackgroundOpacity(): Float

    fun setBackgroundOpacity(value: Float)

    fun getTheme(): Boolean

    fun setTheme(isDark: Boolean)

    fun setBackgroundImage(image: Bitmap)

    fun getBackgroundImage(): Bitmap
}