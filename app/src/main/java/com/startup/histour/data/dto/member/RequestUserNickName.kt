package com.startup.histour.data.dto.member

import com.google.gson.annotations.SerializedName

data class RequestUserNickName(
    @SerializedName("name")
    val name: String
)