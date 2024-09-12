package com.startup.histour.data.datastore

import androidx.datastore.core.DataStore
import com.startup.histour.UserInfo
import com.startup.histour.UserInfo.CharacterInfo
import com.startup.histour.presentation.model.CharacterModel
import com.startup.histour.presentation.model.CharacterModel.Companion.toCharacterModel
import com.startup.histour.presentation.model.UserInfoModel
import com.startup.histour.presentation.model.UserInfoModel.Companion.toUserInfoMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserInfoDataStoreProviderImpl @Inject constructor(private val userInfoDataStore: DataStore<UserInfo>) :
    UserInfoDataStoreProvider {
    override suspend fun setCharacterInfo(characterInfo: CharacterInfo) {
        userInfoDataStore.updateData { preferences ->
            preferences
                .toBuilder()
                .setCharacterInfo(characterInfo)
                .build()
        }
    }

    override suspend fun setPlaceId(placeId: String) {
        userInfoDataStore.updateData { preferences ->
            preferences
                .toBuilder()
                .setPlaceId(placeId)
                .build()
        }
    }

    override suspend fun setUserInfo(
        userName: String,
        profileImageUrl: String,
        characterInfo: CharacterInfo
    ) {
        userInfoDataStore.updateData { preferences ->
            preferences
                .toBuilder()
                .setUserName(userName)
                .setProfileImageUrl(profileImageUrl)
                .setCharacterInfo(characterInfo)
                .build()
        }
    }

    override suspend fun getUserInfo(): UserInfoModel {
        return (userInfoDataStore.data
            .firstOrNull() ?: UserInfo.getDefaultInstance()).toUserInfoMode()
    }

    override suspend fun getPlaceId(): Int = userInfoDataStore.data.firstOrNull()?.placeId?.toIntOrNull() ?: -1

    override suspend fun getCharacterInfo(): CharacterModel {
        return (userInfoDataStore.data.firstOrNull()?.characterInfo
            ?: CharacterInfo.getDefaultInstance()).toCharacterModel()
    }

    override fun getUserInfoFlow(): Flow<UserInfoModel> {
        return userInfoDataStore.data.map { it.toUserInfoMode() }
    }

    override fun getCharacterInfoFlow(): Flow<CharacterModel> {
        return userInfoDataStore.data.map { it.characterInfo.toCharacterModel() }
    }

    override suspend fun clearAllData() {
        userInfoDataStore.updateData { currentData ->
            UserInfo.getDefaultInstance()
        }
    }
}