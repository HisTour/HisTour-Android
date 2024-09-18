package com.startup.histour.data.dto.sse

import com.google.gson.annotations.SerializedName

data class ResponseGetUrlDto(
    @SerializedName("url")
    val url: String?
)
