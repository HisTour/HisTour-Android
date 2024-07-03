package com.startup.histour.domain.base

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<R, P>(
    private val coroutineScope: CoroutineScope,
    private val preExecutionContext: CoroutineContext,
    private val postExecutionContext: CoroutineContext,
) {

    private var useCaseJob: Job? = null

    enum class LaunchPolicy {
        RUN_EXIST_JOB_IF_LAUNCHED,
        CANCEL_AND_RELAUNCH,
    }

    protected abstract suspend fun buildUseCase(params: P): Flow<R>

    @Suppress("UNCHECKED_CAST")
    fun launch(
        launchScope: CoroutineScope?,
        launchPolicy: LaunchPolicy = LaunchPolicy.RUN_EXIST_JOB_IF_LAUNCHED,
        params: P = Unit as P,
        onEach: (R) -> Unit,
        onError: (Throwable) -> Unit,
        onCanceled: () -> Unit = {},
        onCompleted: () -> Unit = {},
    ) {
        launch(
            launchScope = launchScope,
            launchPolicy = launchPolicy,
            params = params,
            onMap = { it },
            onEach = onEach,
            onError = onError,
            onCanceled = onCanceled,
            onCompleted = onCompleted,
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun <MR> launch(
        launchPolicy: LaunchPolicy = LaunchPolicy.RUN_EXIST_JOB_IF_LAUNCHED,
        launchScope: CoroutineScope? = null,
        params: P = Unit as P,
        onMap: (R) -> MR,
        onEach: (MR) -> Unit,
        onError: (Throwable) -> Unit,
        onCanceled: () -> Unit = {},
        onCompleted: () -> Unit = {},
    ) {
        if (isJobRunning()) {
            when (launchPolicy) {
                LaunchPolicy.RUN_EXIST_JOB_IF_LAUNCHED -> {
                    return
                }

                LaunchPolicy.CANCEL_AND_RELAUNCH -> {
                    cancelJob()
                }
            }
        }

        useCaseJob = (launchScope ?: coroutineScope).launch {
            buildUseCase(params)
                .map { onMap(it) }
                .flowOn(preExecutionContext)
                .onEach { onEach(it) }
                .onCompletion {
                    when (it) {
                        is CancellationException -> onCanceled()
                    }
                    onCompleted()
                }
                .catch { onError(it) }
                .flowOn(postExecutionContext)
                .collect()
        }

    }

    private fun cancelJob() {
        useCaseJob?.cancel()
    }

    private fun isJobRunning() =
        useCaseJob?.isActive ?: false
}
