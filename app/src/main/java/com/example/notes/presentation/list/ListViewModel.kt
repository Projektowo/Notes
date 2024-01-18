package com.example.notes.presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.model.DomainNote
import com.example.notes.domain.model.DomainNotePriorityType
import com.example.notes.domain.usecase.DeleteNoteByIdUseCase
import com.example.notes.domain.usecase.GetNoteByIdUseCase
import com.example.notes.domain.usecase.InsertNoteUseCase
import com.example.notes.domain.usecase.ObserveNotesUseCase
import com.example.notes.presentation.mapper.NoteOnViewMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val observeNotesUseCase: ObserveNotesUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val deleteNoteByIdUseCase: DeleteNoteByIdUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val noteOnViewMapper: NoteOnViewMapper,
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
    }
}
