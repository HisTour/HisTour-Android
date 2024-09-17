package com.startup.histour.data.repository

import com.startup.histour.data.datasource.remote.mission.MissionDataSource
import com.startup.histour.data.dto.mission.RequestQuizGrade
import com.startup.histour.data.dto.mission.RequestUnlockMission
import com.startup.histour.data.dto.mission.ResponseGradeQuizDto
import com.startup.histour.data.dto.mission.ResponseMissionDto
import com.startup.histour.data.dto.mission.ResponseQuizDto
import com.startup.histour.domain.repository.MissionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(private val missionDataSource: MissionDataSource) :
    MissionRepository {
    override suspend fun getMissions(placeId: String): Flow<ResponseMissionDto> = flow {
        emit(missionDataSource.getMissions(placeId))
    }

    override suspend fun unlockAndChoiceMission(
        request: RequestUnlockMission
    ) {
        missionDataSource.clearSubMission(request)
    }

    override suspend fun getQuiz(missionId: String): Flow<ResponseQuizDto> = flow {
        emit(missionDataSource.getQuiz(missionId))
    }

    override suspend fun gradeQuiz(requestQuizGrade: RequestQuizGrade) = flow {
        emit(missionDataSource.gradeQuiz(requestQuizGrade))
    }
}