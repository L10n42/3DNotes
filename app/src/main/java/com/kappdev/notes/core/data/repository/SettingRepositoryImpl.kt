package com.kappdev.notes.core.data.repository

import android.content.Context
import com.kappdev.notes.core.domain.repository.SettingRepository

class SettingRepositoryImpl(val context: Context): SettingRepository {

    private val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getBackgroundOpacity(): Float {
        return sharedPreferences.getFloat(KEY_BACKGROUND_OPACITY, 0f)
    }

    override fun setBackgroundOpacity(value: Float) {
        editor.putFloat(KEY_BACKGROUND_OPACITY, value).apply()
    }

    override fun getTheme(): Boolean {
        return sharedPreferences.getBoolean(KEY_THEME, false)
    }

    override fun setTheme(isDark: Boolean) {
        editor.putBoolean(KEY_THEME, isDark).apply()
    }


    companion object {
        const val NAME = "settings"

        const val KEY_THEME = "is_theme_dark"
        const val KEY_BACKGROUND_OPACITY = "background_opacity"
    }
}