package com.kappdev.notes.core.presentation.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class PermissionViewModel: ViewModel() {

    private val permissionToRequest = arrayOf(
        Manifest.permission.CAMERA
    )

    val unGrantedPermissions = mutableStateListOf<String>()
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }

    fun checkPermissions(context: Context): Array<String> {
        unGrantedPermissions.clear()
        permissionToRequest.forEach { permission ->
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                unGrantedPermissions.add(permission)
            }
        }
        return unGrantedPermissions.toTypedArray()
    }
}