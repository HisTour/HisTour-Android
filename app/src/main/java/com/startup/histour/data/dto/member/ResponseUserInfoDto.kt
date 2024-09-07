package com.startup.histour.data.dto.member

import com.google.gson.annotations.SerializedName
import com.startup.histour.data.dto.character.ResponseCharacter

data class ResponseUserInfoDto(
    @SerializedName("userName")
    val userName: String?,
    @SerializedName("profileImageUrl")
    val profileImageUrl: String?,
    @SerializedName("characterResponse")
    val characterResponse: List<ResponseCharacter>?
)