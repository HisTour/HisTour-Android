package com.startup.histour.data.dto.member

import com.google.gson.annotations.SerializedName

data class RequestUserCharacter(
    @SerializedName("characterId")
    val characterId: Int,
    @SerializedName("username")
    val username: String
)