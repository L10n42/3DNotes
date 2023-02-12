package com.kappdev.notes.feature_notes.presentation.notes_screen

import androidx.lifecycle.ViewModel
import com.kappdev.notes.feature_notes.domain.use_cases.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {

}