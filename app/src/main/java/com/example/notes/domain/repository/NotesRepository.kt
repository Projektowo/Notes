package com.example.notes.domain.repository

import com.example.notes.domain.model.DomainNote
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun observeNotes(): Flow<List<DomainNote>>

    suspend fun insertNote(domainNote: DomainNote): Long

    suspend fun getNoteById(noteId: Int): DomainNote

    suspend fun deleteNoteById(noteId: Int)

    suspend fun updateNote(domainNote: DomainNote)
}
