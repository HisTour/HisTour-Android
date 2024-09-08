package com.startup.histour.domain.repository

import com.startup.histour.data.dto.history.ResponseHistoryHolidayDto
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun getNationalHolidays(): Flow<ResponseHistoryHolidayDto>
}