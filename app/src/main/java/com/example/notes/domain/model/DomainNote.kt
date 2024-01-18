package com.example.notes.domain.model

import java.time.LocalDateTime

data class DomainNote(
    val id: Int? = null,
    val title: String? = null,
    val content: String? = null,
    val categoryColorType: DomainCategoryColorType? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val priorityType: DomainNotePriorityType? = null,
)
