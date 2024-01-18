package com.example.notes.data.local.repository

import com.example.notes.data.local.LocalSettingsDataSource
import com.example.notes.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val localSettingsDataSource: LocalSettingsDataSource,
) : SettingsRepository {

    override fun observePriorityHighDaysInterval(): Flow<Int> =
        localSettingsDataSource.observePriorityHighDaysInterval()


    override suspend fun setPriorityHighDaysInterval(days: Int) {
        localSettingsDataSource.setPriorityHighDaysInterval(days)
    }

    override fun observePriorityMediumDaysInterval(): Flow<Int> =
        localSettingsDataSource.observePriorityMediumDaysInterval()


    override suspend fun setPriorityMediumDaysInterval(days: Int) {
        localSettingsDataSource.setPriorityMediumDaysInterval(days)
    }


}
