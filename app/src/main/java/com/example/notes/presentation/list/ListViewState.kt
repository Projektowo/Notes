package com.example.notes.presentation.list

import com.example.notes.presentation.model.NoteOnView

data class ListViewState(
    val notes: List<NoteOnView> = emptyList()
)
