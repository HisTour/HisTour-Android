package com.startup.histour.presentation.util

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

abstract class FragmentPermissionRequestDelegator(
    private val fragment: Fragment,
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
        fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ::onPermissionRequestCallback,
        )

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean =
        fragment.shouldShowRequestPermissionRationale(permission)
}
