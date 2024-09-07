package com.startup.histour.domain.usecase.member

import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.data.dto.member.ResponseUserInfoDto
import com.startup.histour.domain.base.BaseUseCase
import com.startup.histour.domain.repository.MemberRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ViewModelScoped
class GetMyUserDataUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    @IOScope coroutineScope: CoroutineScope,
    @IO preExecutionContext: CoroutineContext,
    @Main postExecutionContext: CoroutineContext,
) : BaseUseCase<ResponseUserInfoDto, Unit>(
    coroutineScope = coroutineScope,
    preExecutionContext = preExecutionContext,
    postExecutionContext = postExecutionContext
) {
    override suspend fun buildUseCase(params: Unit): Flow<ResponseUserInfoDto> = memberRepository.getUserInfo()
}