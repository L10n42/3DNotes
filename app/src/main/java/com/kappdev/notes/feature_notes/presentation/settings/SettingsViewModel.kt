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

    init {
        _theme.value = repository.getTheme()
    }

    fun changeTheme(isThemeDark: Boolean) {
        _theme.value = isThemeDark
        repository.setTheme(isThemeDark)
    }
}