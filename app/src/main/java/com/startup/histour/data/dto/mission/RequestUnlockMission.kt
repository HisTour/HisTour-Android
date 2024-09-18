package com.startup.histour.data.dto.mission

import com.google.gson.annotations.SerializedName

data class RequestUnlockMission(
    @SerializedName("completedMissionId")
    val completedMissionId: Int? = null,
    @SerializedName("nextMissionId")
    val nextMissionId: Int? = null
)