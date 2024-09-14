package com.startup.histour.presentation.model

data class SubMissionModel (
    val clearedQuizCount: Int,
    val id: Int,
    val name: String,
    val state: String,
    val totalQuizCount: Int,
    val type: String
)