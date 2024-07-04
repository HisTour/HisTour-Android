package com.startup.histour.presentation.util

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

abstract class ActivityPermissionRequestDelegator(
    private val activity: ComponentActivity,
    doOnGranted: () -> Unit,
    doOnShouldShowRequestPermissionRationale: (List<String>) -> Unit = {},
    doOnNeverAskAgain: (List<String>) -> Unit = {},
) : PermissionRequestDelegator(
    doOnGranted = doOnGranted,
    doOnNeverAskAgain = doOnNeverAskAgain,
    doOnShouldShowRequestPermissionRationale = doOnShouldShowRequestPermissionRationale,
) {
    abstract override val requestPermissions: Array<String>

    override val permissionLauncher =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ::onPermissionRequestCallback,
        )

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean =
        activity.shouldShowRequestPermissionRationale(permission)
}
