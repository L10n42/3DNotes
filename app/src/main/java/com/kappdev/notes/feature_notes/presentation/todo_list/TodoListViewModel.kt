package com.kappdev.notes.feature_notes.presentation.todo_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.core.presentation.navigation.Screen
import com.kappdev.notes.feature_notes.domain.model.Folder
import com.kappdev.notes.feature_notes.domain.model.Todo
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import com.kappdev.notes.feature_notes.domain.util.IdGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {
    private var folderId: Long? = null
    var editingTodo: Todo? = null
        private set
    var allFolders: List<Folder> = emptyList()
        private set

    private val _navigate = mutableStateOf<String?>(null)
    val navigate: State<String?> = _navigate

    private val _currentTodoListId = mutableStateOf<Long>(0)
    val currentTodoListId: State<Long> = _currentTodoListId

    private val _openBottomSheet = mutableStateOf(false)
    val openBottomSheet: State<Boolean> = _openBottomSheet

    private val _todoListName = mutableStateOf("")
    val todoListName: State<String> = _todoListName

    private val _currentValue = mutableStateOf(TextFieldValue(text = "", selection = TextRange.Zero))
    val currentValue: State<TextFieldValue> = _currentValue

    val todoList = mutableStateListOf<Todo>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            allFolders = notesUseCases.getFolders.list()
        }
    }

    fun moveTo(folderId: Long) {
        viewModelScope.launch(Dispatchers.IO){
            if (currentTodoListId.value > 0) {
                //notesUseCases.moveTo(currentNoteId.value, folderId)
                //makeToast(R.string.msg_note_moved)
            }
        }
    }


    fun getTodoListById() {
        viewModelScope.launch(Dispatchers.IO) {
            val editTodoList = notesUseCases.getTodoListById(currentTodoListId.value)
            fillData(editTodoList)
        }
    }

    private fun fillData(todoList: TodoList) {
        setName(todoList.name)
    }

    fun setCurrentValue(value: TextFieldValue) { _currentValue.value = value }

    fun setName(name: String) { _todoListName.value = name }

    fun saveEdit() {
        editingTodo?.let {
            updateTodo(
                it.copy(text = currentValue.value.text)
            )
            clearValue()
        }
    }

    fun updateTodo(todo: Todo) {
        val updatedList = todoList.map { if (it.id == todo.id) todo else it }
        todoList.clear()
        todoList.addAll(updatedList)
    }

    fun addTodo() {
        todoList.add(
            Todo(
                checked = false,
                text = currentValue.value.text,
                id = IdGenerator.generateRandId()
            )
        )
        clearValue()
    }

    fun editTodo(todo: Todo) {
        editingTodo = todo
        _currentValue.value = currentValue.value.copy(
            text = todo.text, selection = TextRange(todo.text.length)
        )
    }

    fun removeTodo(todo: Todo) { todoList.remove(todo) }

    private fun clearValue() {
        _currentValue.value = currentValue.value.copy(text = "")
        editingTodo?.let { editingTodo = null }
    }

    fun setEditTodoListId(id: Long) { _currentTodoListId.value = id }

    fun setFolderId(id: Long) { folderId = id }

    fun openBottomSheet() { _openBottomSheet.value = true }
    fun closeBottomSheet() { _openBottomSheet.value = false }

    fun navigateBack() {
        if (folderId != null && folderId!! > 0.toLong()) {
            navigate(Screen.FolderScreen.route.plus("folderId=$folderId"))
        } else {
            navigate(Screen.Notes.route)
        }
    }

    fun navigate(route: String) { _navigate.value = route }
    fun clearNavigateRoute() { _navigate.value = null }

}