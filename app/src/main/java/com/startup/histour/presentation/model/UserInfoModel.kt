package com.startup.histour.presentation.model

import com.startup.histour.UserInfo
import com.startup.histour.presentation.model.CharacterModel.Companion.toCharacterModel

data class UserInfoModel(
    val userName: String,
    val profileImageUrl: String,
    val placeId: Int,
    val character: CharacterModel
) {
    companion object {
        fun UserInfo?.toUserInfoMode(): UserInfoModel = this?.let {
            UserInfoModel(
                userName = userName.orEmpty(),
                profileImageUrl = profileImageUrl.orEmpty(),
                placeId = (placeId.toIntOrNull() ?: -1),
                character = (characterInfo)?.toCharacterModel() ?: CharacterModel.orEmpty()
            )
        } ?: orEmpty()

        fun orEmpty() = UserInfoModel(
            userName = "",
            profileImageUrl = "",
            placeId = -1,
            character = CharacterModel.orEmpty()
        )
    }
}