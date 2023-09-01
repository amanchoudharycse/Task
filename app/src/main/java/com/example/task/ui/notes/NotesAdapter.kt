package com.example.task.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task.data.model.Note
import com.example.task.databinding.ItemNotesBinding

class NotesAdapter(private val callback: NotesAdapterCallback)
  : ListAdapter<Note, NotesAdapter.NotesHolder>(NOTE_DIFF) {

  inner class NotesHolder(private val binding: ItemNotesBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note) {
      binding.notes = note

      binding.ivEdit.setOnClickListener {
        callback.onEditClick(note)
      }

      binding.ivDelete.setOnClickListener {
        callback.onDeleteClick(note)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
    val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return NotesHolder(binding)
  }

  override fun onBindViewHolder(holder: NotesHolder, position: Int) {
    holder.bind(getItem(holder.adapterPosition))
  }

  companion object {
    val NOTE_DIFF = object : DiffUtil.ItemCallback<Note>() {
      override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
      }
    }
  }
}

interface NotesAdapterCallback {
  fun onEditClick(note: Note)
  fun onDeleteClick(note: Note)
}
