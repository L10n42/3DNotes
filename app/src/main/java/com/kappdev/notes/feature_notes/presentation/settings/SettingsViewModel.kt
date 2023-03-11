package com.kappdev.notes.feature_notes.presentation.settings

import android.net.Uri
import android.os.Message
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.R
import com.kappdev.notes.core.domain.repository.SettingRepository
import com.kappdev.notes.feature_notes.domain.repository.StorageRepository
import com.kappdev.notes.feature_notes.domain.use_cases.DownloadImage
import com.kappdev.notes.feature_notes.domain.use_cases.DownloadImageException
import com.kappdev.notes.feature_notes.domain.util.Toaster
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingRepository,
    private val storage: StorageRepository,
    private val downloadImage: DownloadImage,
    private val toaster: Toaster
) : ViewModel() {

    val imagesUris = mutableStateListOf<Uri>()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _theme = mutableStateOf(false)
    val theme: State<Boolean> = _theme

    private val _backgroundOpacity = mutableStateOf(0f)
    val backgroundOpacity: State<Float> = _backgroundOpacity

    init {
        _theme.value = repository.getTheme()
        _backgroundOpacity.value = repository.getBackgroundOpacity()
    }

    fun onScreenLoading() {
        getImagesFromStorage()
    }

    private fun getImagesFromStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            if (imagesUris.isNotEmpty()) imagesUris.clear()
            storage.getImages(
                onSuccess = { uris -> imagesUris.addAll(uris) },
                onFailure = { toaster.makeToast(R.string.msg_capture_image_error) }
            )
        }
    }

    fun setBackgroundImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val image = downloadImage(uri)
                repository.setBackgroundImage(image)
            } catch (e: DownloadImageException) {
                toaster.makeToast(e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun makeToast(msgResId: Int) = toaster.makeToast(msgResId)

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