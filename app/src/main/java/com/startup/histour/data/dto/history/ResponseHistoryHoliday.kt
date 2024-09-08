package com.startup.histour.data.dto.history

import com.google.gson.annotations.SerializedName

data class ResponseHistoryHoliday(
    @SerializedName("date")
    val date: String?,
    @SerializedName("name")
    val name: String?
)