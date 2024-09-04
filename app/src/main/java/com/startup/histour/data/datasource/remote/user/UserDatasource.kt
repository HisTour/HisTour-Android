package com.startup.histour.data.datasource.remote.user

import com.startup.histour.data.datasource.remote.provider.RemoteConfigProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDatasource @Inject constructor(private val remoteConfigProvider: RemoteConfigProvider){

    fun getMinAppVersion(): Flow<Long> = remoteConfigProvider.minAppVersion
}