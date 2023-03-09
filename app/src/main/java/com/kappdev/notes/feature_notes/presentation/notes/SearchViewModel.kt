package com.kappdev.notes.feature_notes.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.NoteWithAnnotation
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import com.kappdev.notes.ui.theme.ErrorRed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {
    private var notes: List<Note> = emptyList()

    val searchList = mutableStateListOf<NoteWithAnnotation>()

    private val _isSearching = mutableStateOf(false)
    val isSearching: State<Boolean> = _isSearching

    private val _lastSearchArg = mutableStateOf("")
    val lastSearchArg: State<String> = _lastSearchArg

    private var notesJob: Job? = null
    private var searchJob: Job? = null

    init {
        getNotes()
    }

    private fun getNotes() {
        notesJob?.cancel()
        notesJob = notesUseCases.getNotes().onEach { newNotes ->
            notes = newNotes
        }.launchIn(viewModelScope)
    }

    fun clear() {
        searchList.clear()
        _lastSearchArg.value = ""
        _isSearching.value = false
    }

    fun search(value: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            startSearching()
            _lastSearchArg.value = value
            searchList.clear()
            if (value.trim().isNotBlank()) {
                notes.forEach { note ->
                    val searchArg = value.trim().lowercase()
                    when {
                        note.title.lowercase().contains(searchArg) -> searchList.add(annotate(note))
                        note.content.lowercase().contains(searchArg) -> searchList.add(annotate(note))
                    }
                }
            }
            finishSearching()
        }
    }

    private fun annotate(note: Note): NoteWithAnnotation {
        return note.toAnnotated(
            title = highlightIn(
                text = note.title,
                highlightValue = lastSearchArg.value
            ),
            content = highlightIn(
                text = note.content,
                highlightValue = lastSearchArg.value
            )
        )
    }

    private fun highlightIn(text: String, highlightValue: String): AnnotatedString {

        val highlightStyle = SpanStyle(
            color = ErrorRed
        )
        return buildAnnotatedString {
            text.sliceBy(highlightValue).forEach { textPiece ->
                if (textPiece == highlightValue) {
                    withStyle(highlightStyle) { append(textPiece) }
                } else append(textPiece)
            }
        }
    }

    private fun String.sliceBy(separator: String): List<String> {
        var text = this
        val list = mutableListOf<String>()

        while (text.isNotBlank()) {
            if (text.startsWith(separator)) {
                list.add(separator)
                text = text.removePrefix(separator)
            }

            val value = text.substringBefore(separator, text)
            if (value == text) {
                list.add(value)
                text = text.removePrefix(value)
            } else {
                list.addAll(listOf(value, separator))
                text = text.removePrefix(value + separator)
            }
        }

        return list
    }

    private fun startSearching() { _isSearching.value = true }
    private fun finishSearching() { _isSearching.value = false }
}