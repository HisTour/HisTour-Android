package com.startup.histour.di

import com.startup.histour.data.repository.ChatEventSourceRepositoryImpl
import com.startup.histour.domain.repository.ChatEventSourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsTOEditorEventSourceRepository(chatEventSourceRepository: ChatEventSourceRepositoryImpl): ChatEventSourceRepository
}