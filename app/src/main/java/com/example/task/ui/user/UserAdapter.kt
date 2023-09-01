package com.example.task.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.data.model.User
import com.example.task.databinding.ItemUserBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserHolder>() {

  inner class UserHolder(private val binding:ItemUserBinding)
    : RecyclerView.ViewHolder(binding.root) {
      fun bind(item: User) {

      }
    }

  private val users = ArrayList<User>()

  fun submitList(userList: ArrayList<User>) {
    this.users.clear()
    this.users.addAll(userList)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
    val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return UserHolder(binding)
  }

  override fun getItemCount(): Int = users.size

  override fun onBindViewHolder(holder: UserHolder, position: Int) {
    holder.bind(users[holder.adapterPosition])
  }
}
