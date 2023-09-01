package com.example.task

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.task.databinding.ActivityMainBinding
import com.example.task.ui.availableDevices.AvailableDevicesActivity
import com.example.task.ui.notes.NotesActivity

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    initUi()
  }

  private fun initUi() {
    with(binding) {
      cvTask1.setOnClickListener {
        val intent = Intent(this@MainActivity, AvailableDevicesActivity::class.java)
        startActivity(intent)
      }

      cvTask2.setOnClickListener {
        val intent = Intent(this@MainActivity, NotesActivity::class.java)
        startActivity(intent)
      }
    }
  }
}
