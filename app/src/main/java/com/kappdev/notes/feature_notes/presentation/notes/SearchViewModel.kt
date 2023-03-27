package com.kappdev.notes.feature_notes.presentation.notes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.notes.feature_notes.domain.model.AnnotatedNote
import com.kappdev.notes.feature_notes.domain.model.AnnotatedTodoList
import com.kappdev.notes.feature_notes.domain.model.Note
import com.kappdev.notes.feature_notes.domain.model.TodoList
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import com.kappdev.notes.ui.theme.ErrorRed
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
    private var todoLists: List<TodoList> = emptyList()

    val searchList = mutableStateListOf<Any>()

    private val _isSearching = mutableStateOf(false)
    val isSearching: State<Boolean> = _isSearching

    private val _lastSearchArg = mutableStateOf("")
    val lastSearchArg: State<String> = _lastSearchArg

    private var notesJob: Job? = null
    private var todoListsJob: Job? = null
    private var searchJob: Job? = null

    init {
        getNotes()
        getTodoLists()
    }

    private fun getTodoLists() {
        todoListsJob?.cancel()
        todoListsJob = notesUseCases.getTodoLists().onEach { newTodoLists ->
            todoLists = newTodoLists
        }.launchIn(viewModelScope)
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

            val searchArg = value.trim().lowercase()
            searchList.clear()

            if (searchArg.isNotBlank()) {
                searchInNotes(searchArg)
                searchInTodoLists(searchArg)
            }

            finishSearching()
        }
    }

    private fun searchInTodoLists(value: String) {
        todoLists.forEach { todoList ->
            var alreadyAdded = false

            if (todoList.name.lowercase().contains(value)) {
                searchList.add(annotate(todoList))
                alreadyAdded = true
            }

            if (!alreadyAdded) {
                for (todo in todoList.content) {
                    if (todo.text.lowercase().contains(value)) {
                        searchList.add(annotate(todoList))
                        break
                    }
                }
            }
        }
    }

    private fun searchInNotes(value: String) {
        notes.forEach { note ->
            if (note.title.lowercase().contains(value) ||
                note.content.lowercase().contains(value)) {
                searchList.add(annotate(note))
            }
        }
    }

    private fun annotate(todoList: TodoList): AnnotatedTodoList {
        return todoList.toAnnotated(
            name = highlightIn(
                text = todoList.name
            ),
            content = todoList.content.map { todo ->
                todo.toAnnotated(highlightIn(todo.text))
            }
        )
    }

    private fun annotate(note: Note): AnnotatedNote {
        return note.toAnnotated(
            title = highlightIn(
                text = note.title
            ),
            content = highlightIn(
                text = note.content
            )
        )
    }

    private fun highlightIn(text: String, highlightValue: String = lastSearchArg.value): AnnotatedString {

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
        var text = this.trim()
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