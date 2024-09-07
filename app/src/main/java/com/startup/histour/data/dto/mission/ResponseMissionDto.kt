package com.startup.histour.data.dto.mission

import com.google.gson.annotations.SerializedName

data class ResponseMissionDto(
    @SerializedName("requiredMissionCount")
    val requiredMissionCount: Int?,
    @SerializedName("missions")
    val missions: List<ResponseMission>?
)