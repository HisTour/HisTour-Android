package com.startup.histour.data.dto.mission

import com.google.gson.annotations.SerializedName

data class ResponseGradeQuizDto(
    @SerializedName("isCorrect")
    val isCorrect: Boolean,
    @SerializedName("clearedMissionCount")
    val clearedMissionCount: Int?,
    @SerializedName("requireMissionCount")
    val requireMissionCount: Int,
)
