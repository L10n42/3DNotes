package com.kappdev.notes.feature_notes.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.State
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
    private val editNoteId: Long = 0

    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent

    fun undo() {}
    fun redo() {}

    fun save(onFinish: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesUseCases.insertNote(
                    Note(editNoteId, noteTitle.value, noteContent.value, System.currentTimeMillis())
                )
            } catch (e: InsertNoteException) {
                Log.e("SaveNote", e.message.toString())
            } finally {
                this.launch(Dispatchers.Main) { onFinish() }
            }
        }
    }

    fun setTitle(value: String) { _noteTitle.value = value }
    fun setContent(value: String) { _noteContent.value = value }
}