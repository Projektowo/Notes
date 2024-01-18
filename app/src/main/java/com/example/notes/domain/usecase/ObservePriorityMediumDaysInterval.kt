package com.example.notes.domain.usecase

import com.example.notes.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePriorityMediumDaysInterval @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Int> = settingsRepository.observePriorityMediumDaysInterval()
}
