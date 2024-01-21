package com.example.notes.presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.model.DomainNotePriorityType
import com.example.notes.domain.usecase.DeleteNoteByIdUseCase
import com.example.notes.domain.usecase.ObserveNotesUseCase
import com.example.notes.domain.usecase.ObservePriorityHighDaysInterval
import com.example.notes.domain.usecase.ObservePriorityMediumDaysInterval
import com.example.notes.presentation.mapper.NoteOnViewMapper
import com.example.notes.worker.WorkerStarter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val observeNotesUseCase: ObserveNotesUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase,
    private val observePriorityHighDaysInterval: ObservePriorityHighDaysInterval,
    private val observePriorityMediumDaysInterval: ObservePriorityMediumDaysInterval,
    private val noteOnViewMapper: NoteOnViewMapper,
    private val workerStarter: WorkerStarter
) : ViewModel() {

    var viewState by mutableStateOf(ListViewState())
        private set

    init {
        viewModelScope.launch(IO) {
            observeNotesUseCase().collect {
                withContext(Main) {
                    viewState = viewState.copy(notes = it.map(noteOnViewMapper::invoke))
                }
            }
        }
        viewModelScope.launch(IO) {
            val highPriorityInterval = observePriorityHighDaysInterval().first()
            workerStarter.initReminder(highPriorityInterval, DomainNotePriorityType.HIGH)
        }

        viewModelScope.launch(IO) {
            val highPriorityInterval = observePriorityMediumDaysInterval().first()
            workerStarter.initReminder(highPriorityInterval, DomainNotePriorityType.MEDIUM)
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch(IO) {
            deleteNoteByIdUseCase(noteId)
        }
    }
}
