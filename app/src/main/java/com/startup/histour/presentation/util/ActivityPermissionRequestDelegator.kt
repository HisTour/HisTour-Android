package com.startup.histour.presentation.util

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.startup.histour.presentation.util.extensions.moveToAppDetailSetting

abstract class ActivityPermissionRequestDelegator(
    private val activity: ComponentActivity,
    doOnGranted: () -> Unit,
    doOnShouldShowRequestPermissionRationale: (List<String>) -> Unit = {},
    doOnNeverAskAgain: (List<String>) -> Unit = {
        // 따로 처리 할 필요가 없다면 세팅 창으로 이동
        activity.moveToAppDetailSetting()
    },
) : PermissionRequestDelegator(
    doOnGranted = doOnGranted,
    doOnNeverAskAgain = doOnNeverAskAgain,
    doOnShouldShowRequestPermissionRationale = doOnShouldShowRequestPermissionRationale,
) {
    override val permissionLauncher =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ::onPermissionRequestCallback,
        )

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean =
        activity.shouldShowRequestPermissionRationale(permission)
}
