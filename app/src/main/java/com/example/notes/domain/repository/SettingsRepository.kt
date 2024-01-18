package com.example.notes.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun observePriorityHighDaysInterval(): Flow<Int>

    suspend fun setPriorityHighDaysInterval(days: Int)

    fun observePriorityMediumDaysInterval(): Flow<Int>

    suspend fun setPriorityMediumDaysInterval(days: Int)

}
