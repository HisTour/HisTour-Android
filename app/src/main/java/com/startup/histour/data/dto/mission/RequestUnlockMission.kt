package com.startup.histour.data.dto.mission

import com.google.gson.annotations.SerializedName

data class RequestUnlockMission(
    @SerializedName("completedMissionId")
    val completedMissionId: String,
    @SerializedName("nextMissionId")
    val nextMissionId: String
)