package com.example.task.ui.availableDevices

import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.ItemAvailableDevicesBinding

class AvailableDevicesAdapter(private val callback: AvailableDevicesCallback) :
  RecyclerView.Adapter<AvailableDevicesAdapter.AvailableDevicesViewHolder>() {

  inner class AvailableDevicesViewHolder(private val binding: ItemAvailableDevicesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ScanResult) {
      binding.tvName.text = "SSID: ${item.SSID}, BSSID: ${item.BSSID}"
      binding.cvAvailableDevices.setOnClickListener {
        callback.onItemClick(item)
      }
    }
  }

  private val availableDevices = ArrayList<ScanResult>()

  fun submitList(availableDevices: ArrayList<ScanResult>) {
    this.availableDevices.clear()
    this.availableDevices.addAll(availableDevices)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableDevicesViewHolder {
    val binding =
      ItemAvailableDevicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return AvailableDevicesViewHolder(binding)
  }

  override fun getItemCount(): Int = availableDevices.size

  override fun onBindViewHolder(holder: AvailableDevicesViewHolder, position: Int) {
    holder.bind(availableDevices[holder.adapterPosition])
  }
}

interface AvailableDevicesCallback {
  fun onItemClick(item: ScanResult)
}
