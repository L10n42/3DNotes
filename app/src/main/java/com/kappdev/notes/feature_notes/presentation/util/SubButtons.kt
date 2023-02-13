package com.kappdev.notes.feature_notes.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.TaskAlt
import com.kappdev.notes.R

object SubButtons {
    val NoteText = SubButton(Icons.Default.Description, R.string.note_text, "text_note")
    val ToDoList = SubButton(Icons.Default.TaskAlt, R.string.note_todo_list, "to-do_list")
    val NotesFolder = SubButton(Icons.Default.Folder, R.string.notes_folder, "notes_folder")

    val buttonsList = listOf(NotesFolder, ToDoList, NoteText)
}