package com.david.barbaran.sortingalgorithms.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.barbaran.sortingalgorithms.R
import com.david.barbaran.sortingalgorithms.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    lateinit var list: MutableList<User>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return if (::list.isInitialized) list.size else 0
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.itemView.nameText.text = list[holder.adapterPosition].first_name
        holder.itemView.lastNameText.text = list[holder.adapterPosition].last_name
        holder.itemView.amountText.text = list[holder.adapterPosition].sale.toString()
    }

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}