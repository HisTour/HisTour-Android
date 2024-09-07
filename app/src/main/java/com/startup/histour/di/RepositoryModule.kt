package com.startup.histour.di

import com.startup.histour.data.repository.AttractionRepositoryImpl
import com.startup.histour.data.repository.AuthRepositoryImpl
import com.startup.histour.data.repository.CharacterRepositoryImpl
import com.startup.histour.data.repository.ChatEventSourceRepositoryImpl
import com.startup.histour.data.repository.HistoryRepositoryImpl
import com.startup.histour.data.repository.MemberRepositoryImpl
import com.startup.histour.data.repository.MissionRepositoryImpl
import com.startup.histour.data.repository.PlaceRepositoryImpl
import com.startup.histour.domain.repository.AttractionRepository
import com.startup.histour.domain.repository.AuthRepository
import com.startup.histour.domain.repository.CharacterRepository
import com.startup.histour.domain.repository.ChatEventSourceRepository
import com.startup.histour.domain.repository.HistoryRepository
import com.startup.histour.domain.repository.MemberRepository
import com.startup.histour.domain.repository.MissionRepository
import com.startup.histour.domain.repository.PlaceRepository
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
    abstract fun bindsEditorEventSourceRepository(chatEventSourceRepository: ChatEventSourceRepositoryImpl): ChatEventSourceRepository

    @Singleton
    @Binds
    abstract fun bindsAttractionRepository(attractionRepository: AttractionRepositoryImpl): AttractionRepository

    @Singleton
    @Binds
    abstract fun bindsAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindsCharacterRepository(characterRepository: CharacterRepositoryImpl): CharacterRepository

    @Singleton
    @Binds
    abstract fun bindsHistoryRepository(historyRepository: HistoryRepositoryImpl): HistoryRepository

    @Singleton
    @Binds
    abstract fun bindsMemberRepository(memberRepository: MemberRepositoryImpl): MemberRepository

    @Singleton
    @Binds
    abstract fun bindsMissionRepository(missionRepository: MissionRepositoryImpl): MissionRepository

    @Singleton
    @Binds
    abstract fun bindsPlaceRepository(placeRepository: PlaceRepositoryImpl): PlaceRepository
}