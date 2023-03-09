package com.kappdev.notes.feature_notes.presentation.settings

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.core.domain.repository.SettingRepository
import com.kappdev.notes.feature_notes.domain.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingRepository,
    private val storage: StorageRepository
) : ViewModel() {

    private val _theme = mutableStateOf(false)
    val theme: State<Boolean> = _theme

    private val _backgroundOpacity = mutableStateOf(0f)
    val backgroundOpacity: State<Float> = _backgroundOpacity

    init {
        _theme.value = repository.getTheme()
        _backgroundOpacity.value = repository.getBackgroundOpacity()
        getImagesFromStorage()
    }

    private fun getImagesFromStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            storage.getImages()
        }
    }

    fun setBackgroundImage(uri: Uri) {

    }

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