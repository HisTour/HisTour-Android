package com.startup.histour.data.datastore

import com.startup.histour.UserInfo.CharacterInfo
import com.startup.histour.presentation.model.CharacterModel
import com.startup.histour.presentation.model.UserInfoModel
import kotlinx.coroutines.flow.Flow

interface UserInfoDataStoreProvider {
    suspend fun setCharacterInfo(characterInfo: CharacterInfo)
    suspend fun setPlaceId(placeId: String)
    suspend fun setUserName(userName: String)
    suspend fun setUserInfo(userName: String, profileImageUrl: String, characterInfo: CharacterInfo)
    suspend fun getUserInfo(): UserInfoModel
    suspend fun getPlaceId(): Int
    suspend fun getCharacterInfo(): CharacterModel
    fun getUserInfoFlow(): Flow<UserInfoModel>
    fun getCharacterInfoFlow(): Flow<CharacterModel>
    suspend fun clearAllData()
}