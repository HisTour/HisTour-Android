package com.startup.histour.data.dto.auth

import com.google.gson.annotations.SerializedName

data class ResponseLoginDto(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("username")
    val userName: String?
)
