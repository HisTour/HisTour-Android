package com.startup.histour.domain.usecase.place

import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.domain.base.BaseUseCase
import com.startup.histour.domain.repository.PlaceRepository
import com.startup.histour.presentation.onboarding.model.Place
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ViewModelScoped
class GetMyCurrentTravelPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository,
    @IOScope coroutineScope: CoroutineScope,
    @IO preExecutionContext: CoroutineContext,
    @Main postExecutionContext: CoroutineContext,
) : BaseUseCase<Place, Int>(
    coroutineScope = coroutineScope,
    preExecutionContext = preExecutionContext,
    postExecutionContext = postExecutionContext
) {
    override suspend fun buildUseCase(params: Int): Flow<Place> = placeRepository.getPlaces().mapNotNull { it.places?.find { place -> place.placeId == params }?.toPlace() }
}