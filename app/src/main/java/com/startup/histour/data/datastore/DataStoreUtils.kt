package com.startup.histour.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val TOKEN_DATA_STORE_FILE_NAME = "nadeul_ai_token"

const val ACCESS_TOKEN_KEY_NAME = "AccessToken"
const val REFRESH_ACCESS_TOKEN_KEY_NAME = "RefreshToken"

val Context.userTokenDataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_DATA_STORE_FILE_NAME)
