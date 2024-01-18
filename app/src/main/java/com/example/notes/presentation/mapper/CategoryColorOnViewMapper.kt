package com.example.notes.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.example.notes.domain.model.DomainCategoryColorType
import javax.inject.Inject

class CategoryColorOnViewMapper @Inject constructor() {
    operator fun invoke(domainCategoryColorType: DomainCategoryColorType?): Color =
        when (domainCategoryColorType) {
            DomainCategoryColorType.RED -> COLOR_RED
            DomainCategoryColorType.GREEN -> COLOR_GREEN
            DomainCategoryColorType.BLUE -> COLOR_BLUE
            DomainCategoryColorType.ORANGE -> COLOR_ORANGE
            else -> COLOR_PURPLE
        }

    operator fun invoke(color: Color): DomainCategoryColorType = when (color) {
        COLOR_RED -> DomainCategoryColorType.RED
        COLOR_GREEN -> DomainCategoryColorType.GREEN
        COLOR_BLUE -> DomainCategoryColorType.BLUE
        COLOR_ORANGE -> DomainCategoryColorType.ORANGE
        else -> DomainCategoryColorType.PURPLE
    }

    private companion object {
        val COLOR_RED = Color(204, 35, 35, 255)
        val COLOR_GREEN = Color(37, 184, 32, 255)
        val COLOR_BLUE = Color(39, 100, 221, 255)
        val COLOR_ORANGE = Color(221, 124, 39, 255)
        val COLOR_PURPLE = Color(100, 39, 221, 255)
    }
}
