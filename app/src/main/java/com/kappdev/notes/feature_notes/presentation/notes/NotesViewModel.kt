package com.kappdev.notes.feature_notes.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {
    var allFolders: List<Folder> = emptyList()
        private set
    
    val data = mutableStateListOf<Any>()
    val selectionList = mutableStateListOf<Any>()

    private val _currentBottomSheet = mutableStateOf<NotesBottomSheet?>(null)
    val currentBottomSheet: State<NotesBottomSheet?> = _currentBottomSheet

    private val _searchMode = mutableStateOf(false)
    val searchMode: State<Boolean> = _searchMode

    private val _selectionMode = mutableStateOf(false)
    val selectionMode: State<Boolean> = _selectionMode
    
    private val _navigate = mutableStateOf<String?>(null)
    val navigate: State<String?> = _navigate

    init { getAllFolders() }

    fun createFolder(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultId = notesUseCases.insertFolder(
                Folder(id = 0, name = name, items = 0, timestamp = System.currentTimeMillis())
            )
            if (resultId > 0) updateData()
        }
    }

    fun moveSelectedTo(folderId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val notes = selectionList.mapNotNull { if (it is Note) it else null }
            val todoLists = selectionList.mapNotNull { if (it is TodoList) it else null }
            notesUseCases.moveNotesTo(folderId, notes)
            notesUseCases.moveTodoListsTo(folderId, todoLists)
            switchSelectionModeOffAndClear()
            updateData()
        }
    }

    fun removeSelectedAndReset() {
        viewModelScope.launch(Dispatchers.IO) {
            notesUseCases.multipleRemove.removeAll(selectionList)
            switchSelectionModeOffAndClear()
            updateData()
        }
    }

    private fun updateData() {
        getAllData()
        getAllFolders()
    }

    private fun getAllFolders() {
        viewModelScope.launch(Dispatchers.IO) {
            allFolders = notesUseCases.getFolders.list()
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

    fun foldersNotSelected(): Boolean {
        selectionList.forEach {
            if (it !is Note && it !is TodoList) return false
        }
        return true
    }

    fun openSheet(sheet: NotesBottomSheet) { _currentBottomSheet.value = sheet }
    fun closeSheet() { _currentBottomSheet.value = null }

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