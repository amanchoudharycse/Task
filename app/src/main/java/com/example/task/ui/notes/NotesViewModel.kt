package com.example.task.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.data.model.Note
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class NotesViewModel: ViewModel() {

  private val _noteList = MutableLiveData<List<Note>>()
  val noteList: LiveData<List<Note>> = _noteList

  fun getAllNotes(databaseReference: DatabaseReference) {
    databaseReference.addValueEventListener(object : ValueEventListener {
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        _noteList.value = dataSnapshot.children.map {
          Note.fromJson(it.value as Map<String, Any>)
        }
      }

      override fun onCancelled(databaseError: DatabaseError) {
        // Handle error
      }
    })
  }

  fun add(note: Note, databaseReference: DatabaseReference): Task<Void> {
    val noteId = note.id ?: databaseReference.push().key  // Generates a new ID for the note
    val note2 = Note(noteId, note.title, note.text)
    return databaseReference.child(noteId!!).setValue(note2)
  }

  fun remove(note: Note, databaseReference: DatabaseReference) {
    databaseReference.child(note.id!!).removeValue()
  }
}
