package com.example.task

import android.view.View

fun View.visible(isVisible: Boolean) {
  if (isVisible) {
    this.visibility = View.VISIBLE
  } else {
    this.visibility = View.GONE
  }
}
