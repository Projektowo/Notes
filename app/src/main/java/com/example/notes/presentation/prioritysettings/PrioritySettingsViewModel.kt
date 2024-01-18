package com.example.notes.presentation.prioritysettings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.usecase.ObservePriorityHighDaysInterval
import com.example.notes.domain.usecase.ObservePriorityMediumDaysInterval
import com.example.notes.domain.usecase.SetPriorityHighDaysInterval
import com.example.notes.domain.usecase.SetPriorityMediumDaysInterval
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PrioritySettingsViewModel @Inject constructor(
    private val observePriorityHighDaysInterval: ObservePriorityHighDaysInterval,
    private val observePriorityMediumDaysInterval: ObservePriorityMediumDaysInterval,
    private val setPriorityHighDaysInterval: SetPriorityHighDaysInterval,
    private val setPriorityMediumDaysInterval: SetPriorityMediumDaysInterval
) : ViewModel() {
    var viewState by mutableStateOf(PrioritySettingsViewState())
        private set

    init {
        viewModelScope.launch(IO) {
            observePriorityHighDaysInterval().collect {
                withContext(Main) {
                    viewState = viewState.copy(
                        priorityHighDaysInterval = it,
                    )
                }
            }
        }
        viewModelScope.launch(IO) {
            observePriorityMediumDaysInterval().collect {
                withContext(Main) {
                    viewState = viewState.copy(
                        priorityMediumDaysInterval = it,
                    )
                }
            }
        }
    }

    fun updateHighPriorityInterval(newInterval: Int) {
        if (newInterval <= 0 || newInterval > 10) return
        viewModelScope.launch(IO) {
            setPriorityHighDaysInterval(newInterval)
        }
    }

    fun updateMediumPriorityInterval(newInterval: Int) {
        if (newInterval <= 0 || newInterval > 10) return
        viewModelScope.launch(IO) {
            setPriorityMediumDaysInterval(newInterval)
        }
    }
}
