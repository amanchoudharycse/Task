package com.example.task.ui.availableDevices

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.PermissionManager
import com.example.task.R
import com.example.task.databinding.ActivityAvailableDevicesBinding
import com.example.task.ui.deviceDetail.DeviceDetailActivity

class AvailableDevicesActivity : AppCompatActivity(), AvailableDevicesCallback {

  private lateinit var binding: ActivityAvailableDevicesBinding

  private val requestLocationPermissionLauncher =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
      if (isGranted.not()) {
        Toast.makeText(
          this, "Need location permission to scan nearby wifi",
          Toast.LENGTH_SHORT
        ).show()
      } else {
        getAvailableDevices()
      }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_available_devices)

    binding.rvAvailableDevices.layoutManager = LinearLayoutManager(this)
    binding.rvAvailableDevices.adapter = AvailableDevicesAdapter(this)

    val permission =
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) Manifest.permission.ACCESS_FINE_LOCATION
      else Manifest.permission.ACCESS_COARSE_LOCATION

    if ((PermissionManager.isPermissionGranted(this, permission))
        .not()
    ) {
      requestLocationPermissionLauncher.launch(permission)
    } else {
      getAvailableDevices()
    }

  }

  /**
   * We handle the permission check where this method gets called
   */
  @SuppressLint("MissingPermission")
  private fun getAvailableDevices() {
    val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val availableDevices = wifiManager.scanResults

    (binding.rvAvailableDevices.adapter as AvailableDevicesAdapter).submitList(
      ArrayList(
        availableDevices
      )
    )

    if (availableDevices.isNullOrEmpty()) {
      binding.tvNoDeviceFound.visibility = View.VISIBLE
    } else {
      binding.tvNoDeviceFound.visibility = View.GONE
    }
  }

  override fun onItemClick(item: ScanResult) {
    val intent = Intent(this, DeviceDetailActivity::class.java).apply {
      putExtra(DeviceDetailActivity.KEY_SCAN_RESULT, item)
    }
    startActivity(intent)
  }
}
