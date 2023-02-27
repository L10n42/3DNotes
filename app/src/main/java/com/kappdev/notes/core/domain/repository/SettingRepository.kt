package com.kappdev.notes.core.domain.repository

interface SettingRepository {

    fun getBackgroundOpacity(): Float

    fun setBackgroundOpacity(value: Float)

    fun getTheme(): Boolean

    fun setTheme(isDark: Boolean)
}