package com.startup.histour.data.dto.history

import com.google.gson.annotations.SerializedName

data class ResponseHistoryHolidayDto(
    @SerializedName("holidays")
    val holidays: List<ResponseHistoryHoliday>?
)