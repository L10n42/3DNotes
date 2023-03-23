package com.kappdev.notes.feature_notes.presentation.add_edit_note

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.R
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import com.kappdev.notes.feature_notes.domain.util.Toaster
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    private val toaster: Toaster
) : ViewModel() {
    private var updateBackStack = true
    private var folderId: Long? = null

    var allFolders: List<Folder> = emptyList()
        private set

    var currentStack = 0
        private set

    val textBackStack = mutableStateListOf<String>()

    private var originalTitle = ""
    private var originalContent = ""

    private val _openBottomSheet = mutableStateOf(false)
    val openBottomSheet: State<Boolean> = _openBottomSheet

    private val _navigate = mutableStateOf<String?>(null)
    val navigate: State<String?> = _navigate

    private val _currentNoteId = mutableStateOf<Long>(0)
    val currentNoteId: State<Long> = _currentNoteId

    private val _noteTitle = mutableStateOf(TextFieldValue(text = "", selection = TextRange.Zero))
    val noteTitle: State<TextFieldValue> = _noteTitle

    private val _noteContent = mutableStateOf(TextFieldValue(text = "", selection = TextRange.Zero))
    val noteContent: MutableState<TextFieldValue> = _noteContent

    init {
        viewModelScope.launch(Dispatchers.IO) {
            allFolders = notesUseCases.getFolders.list()
        }
    }

    fun undo() {
        currentStack--
        val value = textBackStack[currentStack]
        _noteContent.value = noteContent.value.copy(
            text = value, selection = TextRange(value.length)
        )
    }

    fun redo() {
        currentStack++
        val value = textBackStack[currentStack]
        updateBackStack = false
        _noteContent.value = noteContent.value.copy(
            text = value, selection = TextRange(value.length)
        )
    }

    fun removeNoteAndNavigateBack() {
        viewModelScope.launch(Dispatchers.IO) {
            notesUseCases.removeNoteById(currentNoteId.value)
            _currentNoteId.value = -1
            navigateBack()
        }
    }

    fun save() {
        if (currentNoteId.value >= 0) {
            viewModelScope.launch(Dispatchers.IO) {
                val noteId = notesUseCases.insertNote(packNote())
                if (noteId > 0) {
                    _currentNoteId.value = noteId
                    getNoteById()
                    makeToast(R.string.msg_saved)
                }
            }
        }
    }

    private fun packNote() = Note(
        id = currentNoteId.value,
        title = noteTitle.value.text,
        content = noteContent.value.text,
        folderId = folderId,
        timestamp = System.currentTimeMillis()
    )

    fun moveTo(folderId: Long) {
        viewModelScope.launch(Dispatchers.IO){
            if (currentNoteId.value > 0) {
                notesUseCases.moveNotesTo(folderId, currentNoteId.value)
                makeToast(R.string.msg_note_moved)
            }
        }
    }


    private fun makeToast(msgRes: Int, duration: Int =  Toast.LENGTH_SHORT) {
        viewModelScope.launch(Dispatchers.Main) {
            toaster.makeToast(msgRes, duration)
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

    fun setTitle(value: TextFieldValue) { _noteTitle.value = value }

    private fun setTitle(text: String) {
        _noteTitle.value = TextFieldValue(text = text, selection = TextRange(text.length))
    }

    fun setEditNoteId(id: Long) {
        _currentNoteId.value = id
        if (id == 0.toLong()) updateBackStack("")
    }

    private fun setContent(text: String) {
        _noteContent.value = TextFieldValue(text = text, selection = TextRange(text.length))
        updateBackStack(text)
    }

    fun setContent(value: TextFieldValue) {
        _noteContent.value = value
        if (updateBackStack) updateBackStack(value.text) else updateBackStack = true
    }

    fun setFolderId(id: Long) { folderId = id }

    fun noteIsNotBlank() = noteContent.value.text.trim().isNotBlank() || noteTitle.value.text.trim().isNotBlank()
    fun noteChanged() = originalTitle != noteTitle.value.text || originalContent != noteContent.value.text

    private fun updateBackStack(value: String) {
        if (currentStack < textBackStack.lastIndex) textBackStack.removeRange(currentStack + 1, textBackStack.size)
        textBackStack.add(value)
        currentStack = textBackStack.lastIndex
    }

    fun copyNote(context: Context) {
        val clipBoard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(noteTitle.value.text, noteContent.value.text)
        clipBoard.setPrimaryClip(clipData)
        toaster.makeToast(R.string.msg_copied)
    }

    fun navigateBack() {
        if (folderId != null && folderId!! > 0.toLong()) {
            navigate(Screen.FolderScreen.route.plus("folderId=$folderId"))
        } else {
            navigate(Screen.Notes.route)
        }
    }
    fun navigate(route: String) { _navigate.value = route }
    fun clearNavigateRoute() { _navigate.value = null }

    fun openBottomSheet() { _openBottomSheet.value = true }
    fun closeBottomSheet() { _openBottomSheet.value = false }
}