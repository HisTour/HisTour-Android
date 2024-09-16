package com.startup.histour.data.dto.mission

import com.google.gson.annotations.SerializedName

data class RequestQuizGrade(
    @SerializedName("quizId")
    val quizId: Int,
    @SerializedName("memberAnswer")
    val memberAnswer: String,
    @SerializedName("isLastTask")
    val isLastTask: Boolean
)