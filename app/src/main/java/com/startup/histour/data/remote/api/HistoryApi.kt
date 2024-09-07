package com.startup.histour.data.remote.api

import com.startup.histour.data.dto.history.ResponseHistoryHolidayDto
import com.startup.histour.data.util.BaseResponse
import retrofit2.http.GET

interface HistoryApi {
    @GET("history/holiday")
    suspend fun getNationalHolidays(): BaseResponse<ResponseHistoryHolidayDto>
}