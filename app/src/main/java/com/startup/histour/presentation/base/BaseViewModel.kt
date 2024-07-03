package com.startup.histour.presentation.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.startup.histour.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {

    abstract val state: State

    private val _event = MutableSharedFlow<BaseEvent>(
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val event = _event.asSharedFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    protected fun notifyEvent(event: BaseEvent) {
        coroutineScope.launch {
            _event.emit(event)
        }
    }

    protected fun <R, P> BaseUseCase<R, P>.executeOnViewModel(
        launchPolicy: BaseUseCase.LaunchPolicy = BaseUseCase.LaunchPolicy.RUN_EXIST_JOB_IF_LAUNCHED,
        launchScope: CoroutineScope? = viewModelScope,
        params: P = Unit as P,
        onEach: (R) -> Unit,
        onError: (Throwable) -> Unit,
        onCanceled: () -> Unit = {},
        onCompleted: () -> Unit = {},
    ) {
        launch(
            launchPolicy = launchPolicy,
            launchScope = launchScope,
            params = params,
            onEach = onEach,
            onError = onError,
            onCanceled = onCanceled,
            onCompleted = onCompleted,
        )
    }

    protected fun <R, P, MR> BaseUseCase<R, P>.executeOnViewModel(
        launchPolicy: BaseUseCase.LaunchPolicy = BaseUseCase.LaunchPolicy.RUN_EXIST_JOB_IF_LAUNCHED,
        launchScope: CoroutineScope? = viewModelScope,
        params: P = Unit as P,
        onMap: (R) -> MR,
        onEach: (MR) -> Unit,
        onError: (Throwable) -> Unit,
        onCanceled: () -> Unit = {},
        onCompleted: () -> Unit = {},
    ) {
        launch(
            launchPolicy = launchPolicy,
            launchScope = launchScope,
            params = params,
            onMap = onMap,
            onEach = onEach,
            onError = onError,
            onCanceled = onCanceled,
            onCompleted = onCompleted,
        )
    }
}