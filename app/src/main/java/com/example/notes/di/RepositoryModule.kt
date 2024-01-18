package com.example.notes.di

import com.example.notes.data.local.LocalSettingsDataSource
import com.example.notes.data.local.NotesDao
import com.example.notes.data.local.repository.NotesRepositoryImpl
import com.example.notes.data.local.repository.SettingsRepositoryImpl
import com.example.notes.domain.mapper.DomainNoteMapper
import com.example.notes.domain.repository.NotesRepository
import com.example.notes.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCharactersRepository(
        notesDao: NotesDao,
        domainNoteMapper: DomainNoteMapper
    ): NotesRepository = NotesRepositoryImpl(
        localNotesDataSource = notesDao,
        domainNoteMapper = domainNoteMapper
    )

    @Singleton
    @Provides
    fun provideSettingsRepository(
        localSettingsDataSource: LocalSettingsDataSource
    ): SettingsRepository =
        SettingsRepositoryImpl(localSettingsDataSource = localSettingsDataSource)
}
