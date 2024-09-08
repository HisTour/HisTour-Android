package com.startup.histour.data.datasource.remote.history

import com.startup.histour.data.dto.history.ResponseHistoryHolidayDto
import com.startup.histour.data.remote.api.HistoryApi
import com.startup.histour.data.util.handleExceptionIfNeed
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryDataSource @Inject constructor(private val historyApi: HistoryApi) {
    suspend fun getNationalHolidays(): ResponseHistoryHolidayDto {
        return handleExceptionIfNeed {
            historyApi.getNationalHolidays().data
        }
    }
}