package com.startup.histour.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.startup.histour.annotation.UserTokenDataStore
import com.startup.histour.data.datastore.ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.REFRESH_ACCESS_TOKEN_KEY_NAME
import com.startup.histour.data.datastore.userTokenDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @UserTokenDataStore
    fun provideSignUpFlowPreferenceDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.userTokenDataStore

    @Provides
    @Named(REFRESH_ACCESS_TOKEN_KEY_NAME)
    fun providesAccessTokenKey(): Preferences.Key<String> = stringPreferencesKey(REFRESH_ACCESS_TOKEN_KEY_NAME)

    @Provides
    @Named(ACCESS_TOKEN_KEY_NAME)
    fun providesRefreshTokenKey(): Preferences.Key<String> = stringPreferencesKey(ACCESS_TOKEN_KEY_NAME)

}