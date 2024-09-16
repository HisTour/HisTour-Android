package com.startup.histour.domain.usecase.mission

import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.data.dto.mission.RequestUnlockMission
import com.startup.histour.domain.base.BaseUseCase
import com.startup.histour.domain.repository.MissionRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ViewModelScoped
class ClearSubMissionUseCase @Inject constructor(
    private val missionRepository: MissionRepository,
    @IOScope coroutineScope: CoroutineScope,
    @IO preExecutionContext: CoroutineContext,
    @Main postExecutionContext: CoroutineContext,
) : BaseUseCase<Boolean, RequestUnlockMission>(
    coroutineScope = coroutineScope,
    preExecutionContext = preExecutionContext,
    postExecutionContext = postExecutionContext
) {
    override suspend fun buildUseCase(
        params : RequestUnlockMission
    ): Flow<Boolean> = missionRepository.clearMission(
        params
    )
}