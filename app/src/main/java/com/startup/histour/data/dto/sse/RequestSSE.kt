package com.startup.histour.data.dto.sse

import org.jetbrains.kotlin.com.google.gson.annotations.SerializedName

data class RequestSSE(
    @SerializedName("input")
    val input: String = ""
)