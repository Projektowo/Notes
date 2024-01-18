package com.example.notes.domain.usecase

import com.example.notes.domain.repository.SettingsRepository
import javax.inject.Inject

class SetPriorityMediumDaysInterval @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(days: Int) {
        settingsRepository.setPriorityMediumDaysInterval(days = days)
    }
}
