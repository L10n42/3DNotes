package com.kappdev.notes.feature_notes.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.feature_notes.domain.model.InsertNoteException
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {

    var currentStack = 0
        private set
    val textBackStack = mutableStateListOf<String>()

    private var originalTitle = ""
    private var originalContent = ""

    private val _currentNoteId = mutableStateOf<Long>(0)
    val currentNoteId: State<Long> = _currentNoteId

    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent

    fun undo() {
        currentStack--
        _noteContent.value = textBackStack[currentStack]
    }
    fun redo() {
        currentStack++
        _noteContent.value = textBackStack[currentStack]
    }

    fun removeNote(onFinish: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            notesUseCases.removeNoteById(currentNoteId.value)
            _currentNoteId.value = -1
            this.launch(Dispatchers.Main) { onFinish() }
        }
    }

    fun save(onFailure: (msg: String) -> Unit, onSuccess: () -> Unit) {
        if (currentNoteId.value >= 0) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val noteId = notesUseCases.insertNote(
                        Note(currentNoteId.value, noteTitle.value, noteContent.value, System.currentTimeMillis())
                    )
                    _currentNoteId.value = noteId
                    getNoteById()
                    this.launch(Dispatchers.Main) { onSuccess() }
                } catch (e: InsertNoteException) {
                    this.launch(Dispatchers.Main) { onFailure(e.message.toString()) }
                }
            }
        }
    }

    fun getNoteById() {
        viewModelScope.launch(Dispatchers.IO) {
            val editNote = notesUseCases.getNoteById(currentNoteId.value)
            fillData(editNote)
        }
    }

    private fun fillData(note: Note) {
        setTitle(note.title)
        setContent(note.content)
        originalTitle = note.title
        originalContent = note.content
    }

    fun setTitle(value: String) { _noteTitle.value = value }

    fun setEditNoteId(id: Long) {
        _currentNoteId.value = id
        if (id == 0.toLong()) updateBackStack("")
    }

    fun setContent(value: String) {
        _noteContent.value = value
        updateBackStack(value)
    }

    fun noteIsNotBlank() = noteContent.value.trim().isNotBlank() || noteTitle.value.trim().isNotBlank()
    fun noteChanged() = originalTitle != noteTitle.value || originalContent != noteContent.value

    private fun updateBackStack(value: String) {
        textBackStack.add(value)
        currentStack = textBackStack.lastIndex
    }
}