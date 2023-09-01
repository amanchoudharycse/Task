package com.example.task

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionManager {

  companion object {

    fun isPermissionGranted(
      context: Context,
      permission: String
    ): Boolean {
      val checkPermission = ActivityCompat.checkSelfPermission(context, permission)
      return checkPermission == PackageManager.PERMISSION_GRANTED
    }

  }
}
