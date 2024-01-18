package com.example.notes.presentation.mapper

import com.example.notes.R
import com.example.notes.domain.model.DomainNotePriorityType
import com.example.notes.presentation.model.PriorityOnView
import javax.inject.Inject

class PriorityOnViewMapper @Inject constructor() {
    operator fun invoke(domainNotePriorityType: DomainNotePriorityType): PriorityOnView =
        when (domainNotePriorityType) {
            DomainNotePriorityType.LOW -> PRIORITY_LOW

            DomainNotePriorityType.MEDIUM -> PRIORITY_MEDIUM

            DomainNotePriorityType.HIGH -> PRIORITY_HIGH

            else -> PRIORITY_UNKNOWN
        }

    operator fun invoke(priorityOnView: PriorityOnView) = when (priorityOnView) {
        PRIORITY_LOW -> DomainNotePriorityType.LOW
        PRIORITY_MEDIUM -> DomainNotePriorityType.MEDIUM
        PRIORITY_HIGH -> DomainNotePriorityType.HIGH
        else -> DomainNotePriorityType.UNKNOWN
    }


    private companion object {
        val PRIORITY_LOW =
            PriorityOnView(
                domainNotePriorityType = DomainNotePriorityType.LOW,
                icon = R.drawable.ic_priority_low,
                title = R.string.low_priority
            )
        val PRIORITY_MEDIUM =
            PriorityOnView(
                domainNotePriorityType = DomainNotePriorityType.MEDIUM,
                icon = R.drawable.ic_priority_medium,
                title = R.string.medium_priority
            )
        val PRIORITY_HIGH =
            PriorityOnView(
                domainNotePriorityType = DomainNotePriorityType.HIGH,
                icon = R.drawable.ic_priority_high,
                title = R.string.high_priority
            )
        val PRIORITY_UNKNOWN =
            PriorityOnView(
                domainNotePriorityType = DomainNotePriorityType.UNKNOWN,
                icon = R.drawable.ic_do_not_disturb,
                title = R.string.none_priority
            )
    }
}
