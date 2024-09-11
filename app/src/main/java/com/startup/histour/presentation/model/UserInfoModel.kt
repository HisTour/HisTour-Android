package com.startup.histour.presentation.model

import com.startup.histour.UserInfo
import com.startup.histour.presentation.model.CharacterModel.Companion.toCharacterModel

data class UserInfoModel(
    val userName: String,
    val profileImageUrl: String,
    val character: CharacterModel
) {
    fun toUserInfo(): UserInfo = UserInfo.newBuilder()
        .setUserName(userName)
        .setProfileImageUrl(profileImageUrl)
        .setCharacterInfo(character.toCharacterInfo())
        .build()

    companion object {
        fun UserInfo?.toUserInfoMode(): UserInfoModel = this?.let {
            UserInfoModel(
                userName = userName.orEmpty(),
                profileImageUrl = profileImageUrl.orEmpty(),
                character = (characterInfo)?.toCharacterModel() ?: CharacterModel.orEmpty()
            )
        } ?: orEmpty()

        fun orEmpty() = UserInfoModel(
            userName = "",
            profileImageUrl = "",
            character = CharacterModel.orEmpty()
        )
    }
}