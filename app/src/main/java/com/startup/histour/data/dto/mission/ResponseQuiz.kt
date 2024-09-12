package com.startup.histour.data.dto.mission

import com.google.gson.annotations.SerializedName

data class ResponseQuiz(
    @SerializedName("answer")
    val answer: String,
    @SerializedName("hint")
    val hint: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("sequence")
    val sequence: Int,
    @SerializedName("type")
    val type: String
)