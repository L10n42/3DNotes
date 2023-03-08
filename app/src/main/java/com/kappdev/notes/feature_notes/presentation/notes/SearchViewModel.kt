package com.kappdev.notes.feature_notes.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {
    private var notes: List<Note> = emptyList()

    val searchList = mutableStateListOf<Note>()

    private val _isSearching = mutableStateOf(false)
    val isSearching: State<Boolean> = _isSearching

    private val _lastSearchArg = mutableStateOf("")
    val lastSearchArg: State<String> = _lastSearchArg

    private var notesJob: Job? = null

    init {
        getNotes()
    }

    private fun getNotes() {
        notesJob?.cancel()
        notesJob = notesUseCases.getNotes().onEach { newNotes ->
            notes = newNotes
        }.launchIn(viewModelScope)
    }

    fun search(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            startSearching()
            _lastSearchArg.value = value
            searchList.clear()
            if (value.trim().isNotBlank()) {
                notes.forEach { note ->
                    val searchArg = value.trim().lowercase()
                    when {
                        note.title.lowercase().contains(searchArg) -> searchList.add(note)
                        note.content.lowercase().contains(searchArg) -> searchList.add(note)
                    }
                }
            }
            finishSearching()
        }
    }

    private fun startSearching() { _isSearching.value = true }
    private fun finishSearching() { _isSearching.value = false }
}