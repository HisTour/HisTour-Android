package com.startup.histour.data.dto.place

data class ResponsePlace(
    val clearedMissionCount: Int,
    val description: String,
    val name: String,
    val placeId: Int,
    val totalMissionCount: Int
)