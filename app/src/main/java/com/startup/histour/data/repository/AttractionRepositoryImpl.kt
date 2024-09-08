package com.startup.histour.data.repository

import com.startup.histour.data.datasource.remote.attraction.AttractionDataSource
import com.startup.histour.data.dto.attraction.ResponseAttractionDto
import com.startup.histour.domain.repository.AttractionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AttractionRepositoryImpl @Inject constructor(private val attractionDataSource: AttractionDataSource) : AttractionRepository {
    override suspend fun getAttractions(): Flow<ResponseAttractionDto> = flow {
        emit(attractionDataSource.getAttractions())
    }
}