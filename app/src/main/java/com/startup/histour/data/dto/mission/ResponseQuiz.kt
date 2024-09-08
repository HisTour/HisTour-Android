package com.startup.histour.data.dto.mission

data class ResponseQuiz(
    val answer: String,
    val hint: String,
    val id: Int,
    val imageUrl: String,
    val sequence: Int,
    val type: String
)