package com.example.task.ui.notes

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.task.data.model.Note
import com.example.task.databinding.DialogAddOrEditNotesBinding

class AddOrEditNotesDialogFragment(
  private val manager: FragmentManager
) : DialogFragment() {

  private lateinit var binding: DialogAddOrEditNotesBinding
  private lateinit var callback: AddOrEditNotesCallback
  private var note: Note? = null

  companion object {
    const val TAG = "AddOrEditNotesDialogFragment"
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DialogAddOrEditNotesBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    initUi()
    addListeners()
  }

  override fun onStart() {
    super.onStart()
    val dialog: Dialog? = dialog
    if (dialog != null) {
      val width = ViewGroup.LayoutParams.MATCH_PARENT
      val height = ViewGroup.LayoutParams.MATCH_PARENT
      dialog.window?.setLayout(width, height)
    }
  }

  private fun initUi() {
    with(binding) {
      note?.let {
        etTitle.setText(it.title)
        etText.setText(it.text)
      }
    }
  }

  private fun addListeners() {
    with(binding) {
      btnSubmit.setOnClickListener {
        val note = Note(
          id = this@AddOrEditNotesDialogFragment.note?.id,
          title = etTitle.text.toString(),
          text = etText.text.toString()
        )
        this@AddOrEditNotesDialogFragment.note = note
        callback.onSubmit(note)
        dismiss()
      }

      btnCancel.setOnClickListener {
        dismiss()
      }
    }
  }

  fun show(
    callback: AddOrEditNotesCallback,
    note: Note?
  ) {
    this.callback = callback
    this.note = note
    super.show(manager, TAG)
  }

}

interface AddOrEditNotesCallback {
  fun onSubmit(note: Note)
}
