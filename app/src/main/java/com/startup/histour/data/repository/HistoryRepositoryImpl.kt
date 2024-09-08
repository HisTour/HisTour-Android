package com.startup.histour.data.repository

import com.startup.histour.data.datasource.remote.history.HistoryDataSource
import com.startup.histour.data.dto.history.ResponseHistoryHolidayDto
import com.startup.histour.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val historyDataSource: HistoryDataSource) : HistoryRepository {
    override suspend fun getNationalHolidays(): Flow<ResponseHistoryHolidayDto> = flow {
        emit(historyDataSource.getNationalHolidays())
    }
}