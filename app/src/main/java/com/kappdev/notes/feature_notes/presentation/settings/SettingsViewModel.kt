package com.kappdev.notes.feature_notes.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kappdev.notes.core.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingRepository
) : ViewModel() {

    private val _theme = mutableStateOf(false)
    val theme: State<Boolean> = _theme

    private val _backgroundOpacity = mutableStateOf(0f)
    val backgroundOpacity: State<Float> = _backgroundOpacity

    init {
        _theme.value = repository.getTheme()
        _backgroundOpacity.value = repository.getBackgroundOpacity()
    }

    fun getBackgroundOpacity() = repository.getBackgroundOpacity()

    fun changeBackgroundOpacity(value: Float) {
        _backgroundOpacity.value = value
    }

    fun saveBackgroundOpacity() {
        repository.setBackgroundOpacity(backgroundOpacity.value)
    }

    fun changeTheme(isThemeDark: Boolean) {
        _theme.value = isThemeDark
        repository.setTheme(isThemeDark)
    }
}