package com.startup.histour.data.dto.sse

import com.google.gson.annotations.SerializedName

data class RequestGetUrl(
    @SerializedName("QA")
    val qa: List<String> = emptyList(),
    @SerializedName("character")
    val character: Int,
    @SerializedName("mission_name")
    val missionName: String = "수원 화성",
    @SerializedName("submission_name")
    val submissionName: String = "B: 연무대, 창룡문 - 창룡문의 비밀",
    @SerializedName("task_sequence")
    val taskSequence: Int = 3
)