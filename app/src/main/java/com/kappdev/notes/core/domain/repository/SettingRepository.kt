package com.kappdev.notes.core.domain.repository

interface SettingRepository {

    fun getTheme(): Boolean

    fun setTheme(isDark: Boolean)
}