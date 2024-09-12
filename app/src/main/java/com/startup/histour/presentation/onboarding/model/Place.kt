package com.startup.histour.presentation.onboarding.model

data class Place(
    val clearedMissionCount: Int,
    val description: String,
    val name: String,
    val placeId: Int,
    val totalMissionCount: Int
) {
    companion object {
        fun orEmpty() = Place(
            0,
            "",
            "",
            -1,
            0
        )
    }
}
