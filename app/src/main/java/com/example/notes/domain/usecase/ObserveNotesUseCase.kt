package com.example.notes.domain.usecase

import com.example.notes.domain.model.DomainNote
import com.example.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke(): Flow<List<DomainNote>> =
        notesRepository.observeNotes()
}
