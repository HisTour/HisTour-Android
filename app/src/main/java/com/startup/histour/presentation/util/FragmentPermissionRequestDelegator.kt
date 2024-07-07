package com.startup.histour.presentation.util

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.startup.histour.presentation.util.extensions.moveToAppDetailSetting

abstract class FragmentPermissionRequestDelegator(
    private val fragment: Fragment,
    doOnGranted: () -> Unit,
    doOnShouldShowRequestPermissionRationale: (List<String>) -> Unit = {},
    doOnNeverAskAgain: (List<String>) -> Unit = {
        // 따로 처리 할 필요가 없다면 세팅 창으로 이동
        fragment.requireContext().moveToAppDetailSetting()
    },
) : PermissionRequestDelegator(
    doOnGranted = doOnGranted,
    doOnNeverAskAgain = doOnNeverAskAgain,
    doOnShouldShowRequestPermissionRationale = doOnShouldShowRequestPermissionRationale,
) {
    override val permissionLauncher =
        fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ::onPermissionRequestCallback,
        )

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean =
        fragment.shouldShowRequestPermissionRationale(permission)
}
