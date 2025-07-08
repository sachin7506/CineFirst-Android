package com.platformcommons.cinefirst.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.platformcommons.cinefirst.R
import com.platformcommons.cinefirst.model.User

class UserPagingAdapter(
    private val onItemClick: (User) -> Unit
) : PagingDataAdapter<User, UserPagingAdapter.ViewHolder>(UserDiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.findViewById(R.id.imgAvatar)
        val firstName: TextView = view.findViewById(R.id.tvFirstName)
        val lastName: TextView = view.findViewById(R.id.tvLastName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position) ?: return
        holder.firstName.text = user.first_name
        holder.lastName.text = user.last_name
        Glide.with(holder.itemView.context).load(user.avatar).circleCrop().into(holder.avatar)

        holder.itemView.setOnClickListener { onItemClick(user) }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(old: User, new: User) = old.id == new.id
    override fun areContentsTheSame(old: User, new: User) = old == new
}
