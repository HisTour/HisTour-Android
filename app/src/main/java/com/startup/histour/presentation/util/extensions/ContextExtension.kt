package com.startup.histour.presentation.util.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log

fun Context.moveToAppDetailSetting() {
    try {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:$packageName")
            startActivity(this)
        }
    } catch (e: Exception) {
        Log.e("ContextExtension", "Context.moveToAppDetailSetting() Error")
    }
}