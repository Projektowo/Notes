package com.example.notes.presentation.model

import androidx.compose.ui.graphics.Color

data class NoteOnView(
    val id: Int = 0,
    val title: String,
    val content: String,
    val categoryColor: Color,
    val createdAt: String,
    val updatedAt: String,
    val priority: PriorityOnView,
)
