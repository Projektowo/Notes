package com.example.notes.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.notes.domain.model.DomainNotePriorityType

data class PriorityOnView(
    val domainNotePriorityType: DomainNotePriorityType,
    @DrawableRes val icon: Int,
    @StringRes val title: Int
)
