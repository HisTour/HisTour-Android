package com.startup.histour.data.dto.place

import com.google.gson.annotations.SerializedName
import com.startup.histour.presentation.onboarding.model.Place

data class ResponsePlace(
    @SerializedName("clearedMissionCount")
    val clearedMissionCount: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("placeId")
    val placeId: Int,
    @SerializedName("totalMissionCount")
    val totalMissionCount: Int
) {
    fun toPlace(): Place = Place(clearedMissionCount, description, name, placeId, totalMissionCount)
}