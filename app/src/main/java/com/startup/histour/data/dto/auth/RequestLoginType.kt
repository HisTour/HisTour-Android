package com.startup.histour.data.dto.auth

import com.google.gson.annotations.SerializedName

data class RequestLoginType(
    @SerializedName("type")
    val type: String
)
