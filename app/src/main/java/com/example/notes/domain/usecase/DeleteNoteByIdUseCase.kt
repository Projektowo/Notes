package com.example.notes.domain.usecase

import com.example.notes.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteByIdUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend operator fun invoke(noteId: Int) = notesRepository.deleteNoteById(noteId)
}
