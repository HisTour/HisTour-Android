package com.startup.histour.domain.usecase.place

import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.domain.base.BaseUseCase
import com.startup.histour.domain.repository.PlaceRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ViewModelScoped
class RecommendTravelPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository,
    @IOScope coroutineScope: CoroutineScope,
    @IO preExecutionContext: CoroutineContext,
    @Main postExecutionContext: CoroutineContext,
) : BaseUseCase<Boolean, String>(
    coroutineScope = coroutineScope,
    preExecutionContext = preExecutionContext,
    postExecutionContext = postExecutionContext
) {
    override suspend fun buildUseCase(params: String): Flow<Boolean> = placeRepository.postRecommendPlace(params)
}