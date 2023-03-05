package com.kappdev.notes.feature_notes.presentation.folder_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.repository.NotesRepository
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {
    var folderId: Long = 0
        private set

    val notes = mutableStateListOf<Note>()

    private val _openBottomSheet = mutableStateOf(false)
    val openBottomSheet: State<Boolean> = _openBottomSheet

    private val _currentFolder = mutableStateOf(Folder.EmptyFolder)
    val currentFolder: State<Folder> = _currentFolder

    private val _navigate = mutableStateOf<String?>(null)
    val navigate: State<String?> = _navigate

    fun removeFolder() {
        viewModelScope.launch(Dispatchers.IO) {
            notesUseCases.removeFolder(currentFolder.value)
        }
    }

    fun updateFolder(newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultId = notesUseCases.insertFolder(
                currentFolder.value.copy(name = newName, timestamp = System.currentTimeMillis())
            )
            if (resultId > 0) getFolderById()
        }
    }

    fun getContent(id: Long) {
        folderId = id
        getFolderById()
        getNotes()
    }

    private fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notes.clear()
            notes.addAll(notesUseCases.getNotesByFolderId(folderId))
        }
    }

    private fun getFolderById() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentFolder.value = notesUseCases.getFolderById(folderId)
        }
    }

    fun navigate(route: String) { _navigate.value = route }
    fun clearNavigateRoute() { _navigate.value = null }

    fun openBottomSheet() { _openBottomSheet.value = true }
    fun closeBottomSheet() { _openBottomSheet.value = false }
}