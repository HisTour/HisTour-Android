package com.startup.histour.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.startup.histour.annotation.UserTokenDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataStoreProviderImpl @Inject constructor(@UserTokenDataStore private val preferencesDataStore: DataStore<Preferences>) : TokenDataStoreProvider {
    override suspend fun <T> putValue(key: Preferences.Key<T>, value: T) {
        preferencesDataStore.edit { it[key] = value }
    }

    override suspend fun <T> getValue(key: Preferences.Key<T>, defaultValue: T): T {
        return preferencesDataStore.data.map {
            it[key]
        }.firstOrNull() ?: defaultValue
    }

    override fun <T> getFlowValue(key: Preferences.Key<T>): Flow<T> {
        return preferencesDataStore.data.mapNotNull { it[key] }
    }

    override suspend fun clearAllData() {
        preferencesDataStore.edit { it.clear() }
    }
}