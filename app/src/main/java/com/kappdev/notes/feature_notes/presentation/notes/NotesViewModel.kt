package com.kappdev.notes.feature_notes.presentation.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {

    val notes = mutableStateListOf<Note>()
    val data = mutableStateListOf<Any>()

    private var notesJob: Job? = null

    init {
        getAllData()
    }

    fun createFolder(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultId = notesUseCases.insertFolder(
                Folder(id = 0, name = name, items = 0, timestamp = System.currentTimeMillis())
            )
            if (resultId > 0) getAllData()
        }
    }

    private fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            val newData = notesUseCases.getAllData()
            data.clear()
            data.addAll(newData)
        }
    }
}