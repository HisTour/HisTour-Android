package com.startup.histour.presentation.util.extensions

import android.content.ActivityNotFoundException
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

fun Context.openInstagramProfile(username: String) {
    // 인스타그램 딥링크 URI
    val instagramUri = Uri.parse("http://instagram.com/_u/$username")
    val intent = Intent(Intent.ACTION_VIEW, instagramUri).apply {
        setPackage("com.instagram.android")
    }
    // 인스타그램 앱이 설치되어 있는지 확인
    try {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            // 인스타그램 앱이 설치되어 있지 않은 경우 웹 브라우저로 열기
            openBrowser("http://instagram.com/$username")
        }
    } catch (e: ActivityNotFoundException) {
        Log.e("LMH", "실행할 인스타 앱을 못 찾음")
        openBrowser("http://instagram.com/$username")
    }
}

fun Context.openBrowser(url: String) {
    try {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(webIntent)
    } catch (e: Exception) {
        Log.e("LMH", "url 이 유효하지 않음")
    }
}