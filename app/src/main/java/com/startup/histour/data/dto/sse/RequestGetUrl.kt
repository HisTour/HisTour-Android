package com.startup.histour.data.dto.sse

import com.google.gson.annotations.SerializedName

data class RequestGetUrl(
    @SerializedName("qa")
    val qa: List<String> = emptyList(),
    @SerializedName("characterId")
    val characterId: Int,
    @SerializedName("quizId")
    val taskId: Int,
)