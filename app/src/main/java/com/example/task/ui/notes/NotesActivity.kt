package com.example.task.ui.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.data.model.Note
import com.example.task.databinding.ActivityNotesBinding
import com.example.task.visible
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NotesActivity : AppCompatActivity(), NotesAdapterCallback, AddOrEditNotesCallback {

  private lateinit var binding: ActivityNotesBinding
  private lateinit var viewModel: NotesViewModel
  private lateinit var databaseReference: DatabaseReference

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_notes)
    viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

    val firebaseDb = FirebaseDatabase.getInstance()
    databaseReference = firebaseDb.getReference("users/12345/notes")

    initUi()
    viewModel.getAllNotes(databaseReference)
  }

  private fun initUi() {
    with(binding) {
      rvNotes.layoutManager = LinearLayoutManager(this@NotesActivity)
      rvNotes.adapter = NotesAdapter(this@NotesActivity)
      observeNotes()

      btnAddMedia.setOnClickListener {
        openAddOrEditNotesDialog()
      }
    }
  }

  private fun observeNotes() {
    viewModel.noteList.observe(this) {
      (binding.rvNotes.adapter as NotesAdapter).submitList(it)

      binding.tvNoNotes.visible(it.isNullOrEmpty())
    }
  }

  private fun openAddOrEditNotesDialog(note: Note? = null) {
    val dialog = AddOrEditNotesDialogFragment(supportFragmentManager)
    dialog.isCancelable = false
    dialog.show(this, note)
  }

  override fun onEditClick(note: Note) {
    openAddOrEditNotesDialog(note)
  }

  override fun onDeleteClick(note: Note) {
    viewModel.remove(note, databaseReference)
  }

  override fun onSubmit(note: Note) {
    viewModel.add(note, databaseReference)
  }
}
