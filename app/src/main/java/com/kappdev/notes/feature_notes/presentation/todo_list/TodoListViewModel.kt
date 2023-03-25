package com.kappdev.notes.feature_notes.presentation.todo_list

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
import com.kappdev.notes.feature_notes.domain.model.Todo
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import com.kappdev.notes.feature_notes.domain.use_cases.ShareTodoList
import com.kappdev.notes.feature_notes.domain.util.IdGenerator
import com.kappdev.notes.feature_notes.domain.util.Toaster
import com.kappdev.notes.feature_notes.domain.util.swap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases,
    private val toaster: Toaster,
    private val shareTodoList: ShareTodoList
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

    private val _todoListEdited = mutableStateOf(false)
    val todoListEdited: State<Boolean> = _todoListEdited

    private val _todoListName = mutableStateOf(TextFieldValue(text = "", selection = TextRange.Zero))
    val todoListName: State<TextFieldValue> = _todoListName

    private val _currentValue = mutableStateOf(TextFieldValue(text = "", selection = TextRange.Zero))
    val currentValue: State<TextFieldValue> = _currentValue

    val todoList = mutableStateListOf<Todo>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            allFolders = notesUseCases.getFolders.list()
        }
    }

    fun saveTodoList() {
        if (currentTodoListId.value >= 0) {
            viewModelScope.launch(Dispatchers.IO) {
                val todoListId = notesUseCases.insertTodoList(packTodoList())
                if (todoListId > 0) {
                    _currentTodoListId.value = todoListId
                    getTodoListById()
                    toaster.makeToast(R.string.msg_saved)
                }
            }
        }
    }

    fun shareCurrentTodoList() {
        shareTodoList(
            packTodoList().copy(content = todoList.reversed())
        )
    }

    private fun packTodoList() = TodoList(
        id = currentTodoListId.value,
        name = todoListName.value.text,
        content = todoList,
        folderId = folderId,
        timestamp = System.currentTimeMillis()
    )

    fun moveTo(folderId: Long) {
        viewModelScope.launch(Dispatchers.IO){
            if (currentTodoListId.value > 0) {
                notesUseCases.moveTodoListsTo(folderId, currentTodoListId.value)
                setFolderId(folderId)
                toaster.makeToast(R.string.msg_todoList_moved)
            }
        }
    }

    fun getTodoListById() {
        viewModelScope.launch(Dispatchers.IO) {
            val editTodoList = notesUseCases.getTodoListById(currentTodoListId.value)
            fillData(editTodoList)
        }
    }

    private fun fillData(value: TodoList) {
        setName(value.name)
        todoList.clear()
        todoList.addAll(value.content)
        _todoListEdited.value = false
    }

    fun setCurrentValue(value: TextFieldValue) { _currentValue.value = value }

    fun setName(name: TextFieldValue) {
        _todoListEdited.value = name.text.isNotBlank() && name.text != todoListName.value.text
        _todoListName.value = name
    }
    private fun setName(name: String) {
        _todoListName.value = todoListName.value.copy(
            text = name, selection = TextRange.Zero
        )
    }

    fun saveEdit() {
        editingTodo?.let {
            updateTodo(
                it.copy(text = currentValue.value.text)
            )
            clearValue()
        }
    }

    fun checkedChange(todo: Todo, isChecked: Boolean) {
        val newTodo = todo.copy(checked = isChecked)
        updateTodo(newTodo)

        moveItemTo(toTop = !isChecked, newTodo)
    }

    private fun updateTodo(todo: Todo) {
        val updatedList = todoList.map { if (it.id == todo.id) todo else it }
        todoList.clear()
        todoList.addAll(updatedList)
        _todoListEdited.value = true
    }

    private fun moveItemTo(toTop: Boolean, item: Todo) {
        var currentIndex = todoList.indexOf(item)

        fun toTopCondition() = currentIndex < todoList.lastIndex && todoList[currentIndex + 1].checked
        fun toBottomCondition() = currentIndex > 0

        if (currentIndex >= 0) {
            while (if (toTop) toTopCondition() else toBottomCondition()) {
                val nextIndex = if (toTop) currentIndex + 1 else currentIndex - 1

                if (nextIndex in 0..todoList.lastIndex) {
                    todoList.swap(currentIndex, nextIndex)
                    currentIndex = nextIndex
                } else break
            }
        }
        _todoListEdited.value = true
    }

    fun addTodo() {
        todoList.add(
            Todo(
                checked = false,
                text = currentValue.value.text,
                id = IdGenerator.generateRandId()
            )
        )
        _todoListEdited.value = true
        clearValue()
    }

    fun removeTodoListAndNavigateBack() {
        viewModelScope.launch(Dispatchers.IO) {
            notesUseCases.removeTodoListById(currentTodoListId.value)
            _currentTodoListId.value = -1
            navigateBack()
        }
    }

    fun editTodo(todo: Todo) {
        editingTodo = todo
        _currentValue.value = currentValue.value.copy(
            text = todo.text, selection = TextRange(todo.text.length)
        )
    }

    fun removeTodo(todo: Todo) {
        todoList.remove(todo)
        _todoListEdited.value = true
    }

    private fun clearValue() {
        _currentValue.value = currentValue.value.copy(text = "")
        editingTodo?.let { editingTodo = null }
    }

    fun setEditTodoListId(id: Long) { _currentTodoListId.value = id }

    fun setFolderId(id: Long) { folderId = id }

    fun canSave() = noteIsNotBlank() && todoListEdited.value
    private fun noteIsNotBlank() = todoList.isNotEmpty() || todoListName.value.text.trim().isNotBlank()

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