package com.startup.histour.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.startup.histour.UserInfo


private const val TOKEN_DATA_STORE_FILE_NAME = "nadeul_ai_token"
private const val USER_INFO_DATA_STORE_FILE_NAME = "userInfo.pd"

const val ACCESS_TOKEN_KEY_NAME = "AccessToken"
const val REFRESH_ACCESS_TOKEN_KEY_NAME = "RefreshToken"

val Context.userTokenDataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_DATA_STORE_FILE_NAME)


val Context.userInfoDataStore: DataStore<UserInfo> by dataStore(
    fileName = USER_INFO_DATA_STORE_FILE_NAME,
    serializer = UserInfoPreferencesSerializer
)
