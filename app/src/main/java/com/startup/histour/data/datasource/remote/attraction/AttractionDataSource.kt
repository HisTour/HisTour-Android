package com.startup.histour.data.datasource.remote.attraction

import com.startup.histour.data.dto.attraction.ResponseAttractionDto
import com.startup.histour.data.remote.api.AttractionApi
import com.startup.histour.data.util.handleExceptionIfNeed
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttractionDataSource @Inject constructor(private val attractionApi: AttractionApi) {
    suspend fun getAttractions(): ResponseAttractionDto {
        return handleExceptionIfNeed {
            attractionApi.getAttractions().data
        }
    }
}