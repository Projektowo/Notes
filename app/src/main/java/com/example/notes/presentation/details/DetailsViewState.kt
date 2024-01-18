package com.example.notes.presentation.details

import com.example.notes.presentation.model.NoteOnView

data class DetailsViewState(
    val noteOnView: NoteOnView,
    val hasChanges: Boolean = false
)
