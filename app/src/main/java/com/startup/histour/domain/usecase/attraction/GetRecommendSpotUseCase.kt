package com.startup.histour.domain.usecase.attraction

import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.data.dto.attraction.ResponseAttractionDto
import com.startup.histour.domain.base.BaseUseCase
import com.startup.histour.domain.repository.AttractionRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ViewModelScoped
class GetRecommendSpotUseCase @Inject constructor(
    private val attractionRepository: AttractionRepository,
    @IOScope coroutineScope: CoroutineScope,
    @IO preExecutionContext: CoroutineContext,
    @Main postExecutionContext: CoroutineContext,
) : BaseUseCase<ResponseAttractionDto, Unit>(
    coroutineScope = coroutineScope,
    preExecutionContext = preExecutionContext,
    postExecutionContext = postExecutionContext
) {
    override suspend fun buildUseCase(params: Unit): Flow<ResponseAttractionDto> = attractionRepository.getAttractions()
}