package com.example.notes.domain.usecase

import com.example.notes.domain.model.DomainNotePriorityType
import com.example.notes.domain.repository.SettingsRepository
import com.example.notes.worker.WorkerStarter
import javax.inject.Inject

class SetPriorityHighDaysInterval @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private var workerStarter: WorkerStarter
) {
    suspend operator fun invoke(days: Int) {
        settingsRepository.setPriorityHighDaysInterval(days = days)
        workerStarter.rescheduleReminder(days, DomainNotePriorityType.HIGH)
    }
}

