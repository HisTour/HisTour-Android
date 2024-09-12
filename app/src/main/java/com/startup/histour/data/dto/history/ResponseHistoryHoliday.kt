package com.startup.histour.data.dto.history

import com.google.gson.annotations.SerializedName
import com.startup.histour.presentation.bundle.model.HistoryHoliday

data class ResponseHistoryHoliday(
    @SerializedName("date")
    val date: String?,
    @SerializedName("name")
    val name: String?
) {
    fun toHistoryHoliday(): HistoryHoliday = HistoryHoliday(
        date = date.orEmpty(),
        name = name.orEmpty()
    )
}