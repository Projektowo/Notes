package com.example.notes.data.local.repository

import com.example.notes.data.local.LocalSettingsDataSource
import com.example.notes.domain.model.DomainNotePriorityType
import com.example.notes.domain.repository.SettingsRepository
import com.example.notes.worker.WorkerStarter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val localSettingsDataSource: LocalSettingsDataSource,
) : SettingsRepository {

    override fun observePriorityHighDaysInterval(): Flow<Int> =
        localSettingsDataSource.observePriorityHighDaysInterval()
            .map {
                it ?: run {
                    setPriorityHighDaysInterval(1)
                    1
                }
            }


    override suspend fun setPriorityHighDaysInterval(days: Int) {
        localSettingsDataSource.setPriorityHighDaysInterval(days)
    }

    override fun observePriorityMediumDaysInterval(): Flow<Int> =
        localSettingsDataSource.observePriorityMediumDaysInterval()
            .map {
                it ?: run {
                    setPriorityMediumDaysInterval(3)
                    3
                }
            }


    override suspend fun setPriorityMediumDaysInterval(days: Int) {
        localSettingsDataSource.setPriorityMediumDaysInterval(days)
    }


}
