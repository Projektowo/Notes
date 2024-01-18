package com.example.notes.domain.mapper

import com.example.notes.data.local.model.entity.NoteEntity
import com.example.notes.domain.model.DomainCategoryColorType
import com.example.notes.domain.model.DomainNote
import com.example.notes.domain.model.DomainNotePriorityType
import java.time.LocalDateTime
import javax.inject.Inject

class DomainNoteMapper @Inject constructor() {

    operator fun invoke(note: NoteEntity): DomainNote = with(note) {
        DomainNote(
            id = id,
            title = title.orEmpty(),
            content = content.orEmpty(),
            categoryColorType = category ?: DomainCategoryColorType.PURPLE,
            createdAt = createdAt ?: LocalDateTime.now(),
            updatedAt = updatedAt ?: LocalDateTime.now(),
            priorityType = priority ?: DomainNotePriorityType.UNKNOWN
        )
    }

    operator fun invoke(domainNote: DomainNote): NoteEntity = with(domainNote) {
        NoteEntity(
            id = id ?: 0,
            title = title.orEmpty(),
            content = content.orEmpty(),
            category = categoryColorType ?: DomainCategoryColorType.PURPLE,
            createdAt = createdAt ?: LocalDateTime.now(),
            updatedAt = updatedAt ?: LocalDateTime.now(),
            priority = priorityType ?: DomainNotePriorityType.UNKNOWN
        )
    }
}
