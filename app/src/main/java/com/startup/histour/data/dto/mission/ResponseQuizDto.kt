package com.startup.histour.data.dto.mission

import com.google.gson.annotations.SerializedName

data class ResponseQuizDto(
    @SerializedName("missionName")
    val missionName: String?,
    @SerializedName("missionType")
    val missionType: String?,
    @SerializedName("quizzes")
    val quizzes: List<ResponseQuiz>?
)
