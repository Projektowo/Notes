package com.example.notes.data.local.repository

import com.example.notes.data.local.NotesDao
import com.example.notes.domain.mapper.DomainNoteMapper
import com.example.notes.domain.model.DomainNote
import com.example.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val localNotesDataSource: NotesDao,
    private val domainNoteMapper: DomainNoteMapper
) : NotesRepository {

    override suspend fun observeNotes(): Flow<List<DomainNote>> =
        localNotesDataSource.observeNotes().map { filteredNotes ->
            filteredNotes.map(domainNoteMapper::invoke)
        }

    override suspend fun insertNote(domainNote: DomainNote) =
        localNotesDataSource.insertNote(domainNoteMapper(domainNote))

    override suspend fun getNoteById(noteId: Int): DomainNote =
        domainNoteMapper(localNotesDataSource.getNoteById(noteId))

    override suspend fun deleteNoteById(noteId: Int) {
        localNotesDataSource.deleteNoteById(noteId)
    }

    override suspend fun updateNote(domainNote: DomainNote) {
        localNotesDataSource.updateNote(domainNoteMapper(domainNote))
    }
}
