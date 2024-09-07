package com.startup.histour.data.dto.character

import com.google.gson.annotations.SerializedName

data class ResponseImage(
    @SerializedName("normalImageUrl")
    val normalImageUrl: String?
)