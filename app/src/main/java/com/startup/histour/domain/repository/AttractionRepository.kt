package com.startup.histour.domain.repository

import com.startup.histour.data.dto.attraction.ResponseAttractionDto
import kotlinx.coroutines.flow.Flow

interface AttractionRepository {
    suspend fun getAttractions(): Flow<ResponseAttractionDto>
}