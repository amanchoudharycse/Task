package com.example.task.data.model

import com.google.firebase.database.DataSnapshot

data class Note(
  var id: String? = null,
  var title: String? = null,
  var text: String? = null,
) {

  companion object {
    fun fromJson(json: Map<String, Any>): Note {
      return Note(
        id = json["id"] as String,
        title = json["title"] as String,
        text = json["text"] as String,
      )
    }
  }
}
