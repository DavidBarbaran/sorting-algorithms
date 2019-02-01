package com.david.barbaran.sortingalgorithms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private lateinit var list: MutableList<User>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return if (::list.isInitialized) list.size else 0
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {

    }

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}