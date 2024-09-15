package com.startup.histour.data.dto.mission

import com.google.gson.annotations.SerializedName

data class ResponseQuizCorrectDto (
    @SerializedName("isCorrect")
    val isCorrect: Boolean,
    @SerializedName("clearedMissionCount")
    val clearedMissionCount: Int?,
    @SerializedName("requiredMissionCount")
    val requiredMissionCount: Int,
)