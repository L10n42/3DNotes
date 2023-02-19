package com.kappdev.notes.feature_notes.presentation.folder_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(

) : ViewModel() {

    private val _navigate = mutableStateOf<String?>(null)
    val navigate: State<String?> = _navigate

    fun navigate(route: String) { _navigate.value = route }
    fun clearNavigateRoute() { _navigate.value = null }
}