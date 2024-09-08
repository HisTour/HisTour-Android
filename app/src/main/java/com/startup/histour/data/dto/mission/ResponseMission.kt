package com.startup.histour.data.dto.mission

import com.google.gson.annotations.SerializedName

data class ResponseMission(
    @SerializedName("clearedQuizCount")
    val clearedQuizCount: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("totalQuizCount")
    val totalQuizCount: Int?,
    @SerializedName("type")
    val type: String?
)