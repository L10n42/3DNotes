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
import okhttp3.internal.userAgent
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {
    
    val data = mutableStateListOf<Any>()
    val selectionList = mutableStateListOf<Any>()

    private val _searchMode = mutableStateOf(false)
    val searchMode: State<Boolean> = _searchMode

    private val _selectionMode = mutableStateOf(false)
    val selectionMode: State<Boolean> = _selectionMode
    
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

    fun removeSelectedAndReset() {
        viewModelScope.launch(Dispatchers.IO) {
            notesUseCases.multipleRemove.removeAll(selectionList)
            switchSelectionModeOffAndClear()
            getAllData()
        }
    }

    fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            val newData = notesUseCases.getAllData()
            data.clear()
            data.addAll(newData)
        }
    }

    fun switchSelectionModeOnAndSelect(item: Any) {
        switchSelectionModeON()
        select(item)
    }

    fun switchSelectionModeOffAndClear() {
        clearSelection()
        switchSelectionModeOFF()
    }

    fun selectedAll() = selectionList.containsAll(data)

    fun selectAllItems() {
        selectionList.clear()
        selectionList.addAll(data)
    }

    fun switchItemSelection(item: Any) {
        if (selectionList.contains(item)) deselect(item) else select(item)
    }

    private fun select(item: Any) { selectionList.add(item) }
    private fun deselect(item: Any) { selectionList.remove(item) }
    fun clearSelection() { selectionList.clear() }

    fun navigate(route: String) { _navigate.value = route }
    fun clearNavigateRoute() { _navigate.value = null }

    fun switchSearchModeON() { _searchMode.value = true }
    fun switchSearchModeOFF() { _searchMode.value = false }

    fun switchSelectionModeON() { _selectionMode.value = true }
    fun switchSelectionModeOFF() { _selectionMode.value = false }
}