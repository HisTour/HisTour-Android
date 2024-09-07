package com.startup.histour.data.dto.character

import com.google.gson.annotations.SerializedName

data class ResponseComment(
    @SerializedName("welcome")
    val welcome: String?
)