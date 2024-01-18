package com.example.notes.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalSettingsDataSource @Inject constructor(
    private val context: Context
) {

    fun observePriorityHighDaysInterval(): Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[PRIORITY_HIGH_DAYS_INTERVAL_KEY] ?: 1
    }

    suspend fun setPriorityHighDaysInterval(days: Int) {
        context.dataStore.edit { preferences ->
            preferences[PRIORITY_HIGH_DAYS_INTERVAL_KEY] = days
        }
    }

    fun observePriorityMediumDaysInterval(): Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[PRIORITY_MEDIUM_DAYS_INTERVAL_KEY] ?: 3
    }

    suspend fun setPriorityMediumDaysInterval(days: Int) {
        context.dataStore.edit { preferences ->
            preferences[PRIORITY_MEDIUM_DAYS_INTERVAL_KEY] = days
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

        private val PRIORITY_HIGH_DAYS_INTERVAL_KEY: Preferences.Key<Int> =
            intPreferencesKey("priority_high_days_interval")

        private val PRIORITY_MEDIUM_DAYS_INTERVAL_KEY: Preferences.Key<Int> =
            intPreferencesKey("priority_medium_days_interval")
    }
}
