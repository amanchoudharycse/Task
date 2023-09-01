package com.example.task.ui.deviceDetail

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.task.R
import com.example.task.databinding.ActivityDeviceDetailBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

class DeviceDetailActivity : AppCompatActivity() {

  private lateinit var binding: ActivityDeviceDetailBinding
  private var scanResult: ScanResult? = null

  @SuppressLint("MissingPermission")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_device_detail)
    getData()
    binding.tvLabel.text = "Device Details: BSSID - ${scanResult?.BSSID}"

    val client = LocationServices.getFusedLocationProviderClient(applicationContext)

    val request = LocationRequest.create()
      .setInterval(2000L)
      .setFastestInterval(2000L)
      .setPriority(Priority.PRIORITY_HIGH_ACCURACY)

    val locationCallback = object : LocationCallback() {
      override fun onLocationResult(result: LocationResult) {
        super.onLocationResult(result)
        result.locations.lastOrNull()
          ?.let { _ ->
            // on location change
            scanResult?.let { scan ->
              val wifiManager =
                applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
              var isDeviceFound = false
              wifiManager.scanResults?.forEach {
                if (scan.BSSID == it.BSSID) {
                  isDeviceFound = true
                  val distance = calculateDistance(it.level, it.frequency)
                  binding.tvDistance.text = "Distance: $distance mm"
                }
              }
              if (isDeviceFound.not()) {
                binding.tvDistance.text = "Device not found"
              }
            }
          }
      }

    }

    client.requestLocationUpdates(
      request,
      locationCallback,
      Looper.getMainLooper()
    )
  }

  private fun getData() {
    scanResult = intent?.extras?.getParcelable(KEY_SCAN_RESULT)
  }

  /**
   * This method calculates the approximate distance
   */
  private fun calculateDistance(levelInDb: Int, freqInMHz: Int): Int {
    val exp = (27.55 - 20 * log10(freqInMHz.toDouble()) + abs(levelInDb)) / 20.0
    return (10.0.pow(exp) * 1000).roundToInt()
  }

  companion object {
    const val KEY_SCAN_RESULT = "KEY_SCAN_RESULT"
  }
}
