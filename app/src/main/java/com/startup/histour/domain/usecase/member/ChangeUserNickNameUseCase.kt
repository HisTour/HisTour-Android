package com.startup.histour.domain.usecase.member

import com.startup.histour.annotation.IO
import com.startup.histour.annotation.IOScope
import com.startup.histour.annotation.Main
import com.startup.histour.data.datastore.UserInfoDataStoreProvider
import com.startup.histour.domain.base.BaseUseCase
import com.startup.histour.domain.repository.MemberRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ViewModelScoped
class ChangeUserNickNameUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val userDataInfoDataStoreProvider: UserInfoDataStoreProvider,
    @IOScope coroutineScope: CoroutineScope,
    @IO preExecutionContext: CoroutineContext,
    @Main postExecutionContext: CoroutineContext,
) : BaseUseCase<Unit, String>(
    coroutineScope = coroutineScope,
    preExecutionContext = preExecutionContext,
    postExecutionContext = postExecutionContext
) {
    override suspend fun buildUseCase(params: String): Flow<Unit> = flow {
        memberRepository.setUserProfile(userDataInfoDataStoreProvider.getCharacterInfo().id, params)
        emit(Unit)
    }
}