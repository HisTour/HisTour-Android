package com.startup.histour.data.repository

import com.startup.histour.data.datasource.remote.place.PlaceDataSource
import com.startup.histour.data.dto.place.ResponsePlaceDto
import com.startup.histour.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(private val placeDataSource: PlaceDataSource) : PlaceRepository {
    override suspend fun getPlaces(): Flow<ResponsePlaceDto> = flow {
        emit(placeDataSource.getPlaces())
    }
}