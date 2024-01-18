package com.example.notes.domain.usecase

import com.example.notes.domain.model.DomainNote
import com.example.notes.domain.repository.NotesRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke(domainNote: DomainNote) = notesRepository.insertNote(domainNote)
}
