package com.example.notes.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.model.DomainCategoryColorType
import com.example.notes.domain.model.DomainNote
import com.example.notes.domain.model.DomainNotePriorityType
import com.example.notes.domain.usecase.DeleteNoteByIdUseCase
import com.example.notes.domain.usecase.GetNoteByIdUseCase
import com.example.notes.domain.usecase.InsertNoteUseCase
import com.example.notes.domain.usecase.UpdateNoteUseCase
import com.example.notes.presentation.mapper.CategoryColorOnViewMapper
import com.example.notes.presentation.mapper.NoteOnViewMapper
import com.example.notes.presentation.mapper.PriorityOnViewMapper
import com.example.notes.presentation.model.PriorityOnView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val noteOnViewMapper: NoteOnViewMapper,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val priorityOnViewMapper: PriorityOnViewMapper,
    private val categoryColorOnViewMapper: CategoryColorOnViewMapper,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var viewState by mutableStateOf(DetailsViewState(noteOnViewMapper(DomainNote())))
        private set

    private var note: DomainNote = DomainNote()
    private var initialNote: DomainNote = DomainNote()

    init {
        val noteId = savedStateHandle.get<String>("noteId")?.toIntOrNull()
        if (noteId != null) {
            getNote(noteId)
        }
    }

    fun updateNote() {
        if (initialNote == DomainNote()) {
            insertNote()
        }
        viewModelScope.launch(IO) {
            updateNoteUseCase(note)
            initialNote = note
            withContext(Main) {
                viewState = viewState.copy(
                    noteOnView = noteOnViewMapper(note),
                    hasChanges = hasChanges()
                )
            }
        }
    }

    fun updateCategory(newCategoryColor: Color) {
        note = note.copy(categoryColorType = categoryColorOnViewMapper(newCategoryColor))
        viewState = viewState.copy(
            noteOnView = noteOnViewMapper(note),
            hasChanges = hasChanges()
        )
    }

    fun updatePriority(newPriorityOnView: PriorityOnView) {
        note = note.copy(priorityType = priorityOnViewMapper(newPriorityOnView))
        viewState = viewState.copy(
            noteOnView = noteOnViewMapper(note),
            hasChanges = hasChanges()
        )
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length > 64) return
        note = note.copy(title = newTitle)
        viewState = viewState.copy(
            noteOnView = noteOnViewMapper(note),
            hasChanges = hasChanges()
        )
    }

    fun updateContent(newContent: String) {
        note = note.copy(content = newContent)
        viewState = viewState.copy(
            noteOnView = noteOnViewMapper(note),
            hasChanges = hasChanges()
        )
    }

    fun getPriorities(): List<PriorityOnView> =
        DomainNotePriorityType.values().toList().map(priorityOnViewMapper::invoke)

    fun getCategories(): List<Color> =
        DomainCategoryColorType.values().toList().map(categoryColorOnViewMapper::invoke)

    private fun insertNote() {
        viewModelScope.launch(IO) {
            insertNoteUseCase(note)

        }
    }

    private fun getNote(noteId: Int) {
        viewModelScope.launch(IO) {
            val result = getNoteByIdUseCase(noteId)
            note = result
            initialNote = result
            withContext(Main) {
                viewState = viewState.copy(noteOnView = noteOnViewMapper(note))
            }
        }
    }

    private fun hasChanges(): Boolean = note != initialNote
}
