package com.startup.histour

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.startup.histour.BuildConfig.NATIVE_APP_KEY
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class HisTourApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, NATIVE_APP_KEY)
    }
}