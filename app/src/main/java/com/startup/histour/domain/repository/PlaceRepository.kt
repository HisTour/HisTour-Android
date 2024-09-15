package com.startup.histour.domain.repository

import com.startup.histour.data.dto.place.ResponsePlaceDto
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun getPlaces(): Flow<ResponsePlaceDto>
    suspend fun postRecommendPlace(content: String): Flow<Boolean>
}