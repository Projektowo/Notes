package com.example.notes.di

import android.content.Context
import com.example.notes.worker.WorkerStarter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Singleton
    @Provides
    fun provideWorkerStarter(@ApplicationContext context: Context) = WorkerStarter(context)
}
