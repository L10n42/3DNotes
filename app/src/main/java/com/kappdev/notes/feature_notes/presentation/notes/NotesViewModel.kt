package com.kappdev.notes.feature_notes.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {
    
    val data = mutableStateListOf<Any>()

    private val _searchMode = mutableStateOf(false)
    val searchMode: State<Boolean> = _searchMode
    
    private val _navigate = mutableStateOf<String?>(null)
    val navigate: State<String?> = _navigate

    fun createFolder(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultId = notesUseCases.insertFolder(
                Folder(id = 0, name = name, items = 0, timestamp = System.currentTimeMillis())
            )
            if (resultId > 0) getAllData()
        }
    }

    fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            val newData = notesUseCases.getAllData()
            data.clear()
            data.addAll(newData)
        }
    }

    fun navigate(route: String) { _navigate.value = route }
    fun clearNavigateRoute() { _navigate.value = null }

    fun switchSearchModeON() { _searchMode.value = true }
    fun switchSearchModeOFF() { _searchMode.value = false }
}