package com.startup.histour.data.remote.dataource.user

import com.startup.histour.data.remote.provider.RemoteConfigProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDatasource @Inject constructor(private val remoteConfigProvider: RemoteConfigProvider){

    fun getMinAppVersion(): Flow<Long> = remoteConfigProvider.minAppVersion
}