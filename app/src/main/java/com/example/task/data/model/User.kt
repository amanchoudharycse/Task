package com.example.task.data.model

data class User(
  var id: String? = null,
  var name: String? = null,
  var listOfNotes: List<Note>
)
