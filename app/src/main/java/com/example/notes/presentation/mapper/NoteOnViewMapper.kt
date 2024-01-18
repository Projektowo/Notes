package com.example.notes.presentation.mapper

import com.example.notes.domain.model.DomainCategoryColorType
import com.example.notes.domain.model.DomainNote
import com.example.notes.domain.model.DomainNotePriorityType
import com.example.notes.presentation.model.NoteOnView
import java.time.LocalDateTime
import javax.inject.Inject

class NoteOnViewMapper @Inject constructor(
    private val localDateTimeOnViewFormatter: LocalDateTimeOnViewFormatter,
    private val categoryColorOnViewMapper: CategoryColorOnViewMapper,
    private val priorityOnViewMapper: PriorityOnViewMapper,
) {

    operator fun invoke(domainNote: DomainNote): NoteOnView = with(domainNote) {
        NoteOnView(
            id = id ?: 0,
            title = title.orEmpty(),
            content = content.orEmpty(),
            categoryColor = categoryColorOnViewMapper(
                categoryColorType ?: DomainCategoryColorType.PURPLE
            ),
            createdAt = localDateTimeOnViewFormatter.toSimpleDate(createdAt ?: LocalDateTime.now()),
            updatedAt = localDateTimeOnViewFormatter.toSimpleDate(updatedAt ?: LocalDateTime.now()),
            priority = priorityOnViewMapper(priorityType ?: DomainNotePriorityType.UNKNOWN)
        )
    }

    operator fun invoke(noteOnView: NoteOnView?): DomainNote = with(noteOnView) {
        DomainNote(
            title = this?.title.orEmpty(),
            content = this?.content.orEmpty(),
            categoryColorType = this?.categoryColor?.let { categoryColorOnViewMapper(it) }
                ?: DomainCategoryColorType.PURPLE,
            priorityType = this?.priority?.let { priorityOnViewMapper(it) }
                ?: DomainNotePriorityType.UNKNOWN
        )
    }
}
