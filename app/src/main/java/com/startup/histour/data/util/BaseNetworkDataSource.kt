package com.startup.histour.data.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseNetworkDataSource {
    protected fun <T> emitRemote(apiCall: suspend () -> T): Flow<T> = flow {
        handleExceptionIfNeed {
            emit(apiCall())
        }
    }
}