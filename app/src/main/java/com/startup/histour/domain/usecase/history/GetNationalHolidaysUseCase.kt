package com.startup.histour.domain.usecase.history

import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.data.dto.history.ResponseHistoryHolidayDto
import com.startup.histour.domain.base.BaseUseCase
import com.startup.histour.domain.repository.HistoryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ViewModelScoped
class GetNationalHolidaysUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
    @IOScope coroutineScope: CoroutineScope,
    @IO preExecutionContext: CoroutineContext,
    @Main postExecutionContext: CoroutineContext,
) : BaseUseCase<ResponseHistoryHolidayDto, Unit>(
    coroutineScope = coroutineScope,
    preExecutionContext = preExecutionContext,
    postExecutionContext = postExecutionContext
) {
    override suspend fun buildUseCase(params: Unit): Flow<ResponseHistoryHolidayDto> = historyRepository.getNationalHolidays()
}