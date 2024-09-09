package com.startup.histour.data.dto.member

import com.google.gson.annotations.SerializedName
import com.startup.histour.data.dto.character.ResponseCharacter
import com.startup.histour.presentation.model.CharacterModel
import com.startup.histour.presentation.model.UserInfoModel

data class ResponseUserInfoDto(
    @SerializedName("userName")
    val userName: String?,
    @SerializedName("profileImageUrl")
    val profileImageUrl: String?,
    @SerializedName("characterResponse")
    val characterResponse: ResponseCharacter?
) {
    fun toUserInfoModel(): UserInfoModel = UserInfoModel(
        userName = userName.orEmpty(),
        profileImageUrl = profileImageUrl.orEmpty(),
        character = characterResponse?.toCharacter() ?: CharacterModel.orEmpty()
    )
}