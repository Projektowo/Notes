package com.example.notes.di

import android.content.Context
import com.example.notes.data.local.LocalSettingsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideLocalSettingsDataSource(
        @ApplicationContext context: Context
    ): LocalSettingsDataSource = LocalSettingsDataSource(context = context)
}
