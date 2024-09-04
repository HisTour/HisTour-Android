package com.startup.histour.data.datasource.remote.provider

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.startup.histour.data.util.KEY_MIN_APP_VERSION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigProvider @Inject constructor() {

    private val remoteConfig by lazy {
        Firebase.remoteConfig.apply {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            }
            setConfigSettingsAsync(configSettings)
        }
    }
    private val remoteConfigHolder: Flow<Map<String, FirebaseRemoteConfigValue>>
        get() = callbackFlow {
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(remoteConfig.all)
                    Log.e("LMH", "RemoteConfig Value ${remoteConfig.all.values.map { it.asString() }}")
                } else {
                    Log.e("LMH", "Exception ${task.exception}")
                }
            }
            awaitClose()
        }
    val minAppVersion: Flow<Long> get() = remoteConfigHolder.mapNotNull { kotlin.runCatching { it[KEY_MIN_APP_VERSION]?.asLong() }.getOrNull() }.catch { emit(1) }.onEmpty { 1 }
}