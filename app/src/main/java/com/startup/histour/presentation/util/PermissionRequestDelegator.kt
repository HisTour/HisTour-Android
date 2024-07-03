package com.startup.histour.presentation.util

import androidx.activity.result.ActivityResultLauncher

abstract class PermissionRequestDelegator(
    protected val doOnGranted: () -> Unit,
    protected val doOnShouldShowRequestPermissionRationale: (List<String>) -> Unit,
    protected val doOnNeverAskAgain: (List<String>) -> Unit,
) {
    /**
     * Notification 권한의 경우 앱에서 최초 1회 거부 했을때 명시적으로 사용자가 권한을 허용하도록 해주어야함
     *
     *  추후 Notification 권한이 필요한 기능 구현이 생긴 경우 Notification 전용 Permission Helper 구현 필요함
     *
     *  이 용도를 위하여 protected로 접근 제한함
     * */
    protected val deniedBeforeLaunchedPermission = mutableListOf<String>()

    abstract val requestPermissions: Array<String>

    abstract val permissionLauncher: ActivityResultLauncher<Array<String>>

    abstract fun shouldShowRequestPermissionRationale(permission: String): Boolean

    protected fun onPermissionRequestCallback(resultMap: Map<String, Boolean>) {
        val deniedPermissions = resultMap
            .filter { it.value.not() }
            .map { it.key }

        if (deniedPermissions.isEmpty()) {
            doOnGranted()
            return
        }

        deniedPermissions
            .filter { shouldShowRequestPermissionRationale(it) }
            .takeIf { it.isNotEmpty() }
            ?.let { doOnShouldShowRequestPermissionRationale(it) }

        deniedPermissions
            .filterNot { shouldShowRequestPermissionRationale(it) }
            .takeIf { it.isNotEmpty() }
            ?.let { doOnNeverAskAgain(it) }
    }

    fun requestPermissionLauncher() {
        requestPermissions
            .also { deniedBeforeLaunchedPermission.clear() }
            .filter { shouldShowRequestPermissionRationale(it) }
            .apply { deniedBeforeLaunchedPermission.addAll(this) }
        permissionLauncher.launch(requestPermissions)
    }
}
