package com.example.detailedregistration.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.detailedregistration.databinding.ItemViewBinding
import com.example.detailedregistration.diffutil.MyDiffUtil
import com.example.detailedregistration.model.Users

class UsersAdapter(private val onItemClicked: (position: Int) -> Unit, ) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    private var oldItemList = emptyList<Users>()
    private var usersList = ArrayList<Users>()
    fun setUsersList(usersList: List<Users>) {
        this.usersList = usersList as ArrayList<Users>

        val diffUtil = MyDiffUtil(oldItemList, usersList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldItemList = usersList
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.username.text = usersList[position].username

        holder.itemView.setOnClickListener {
            onItemClicked(position)
        }

    }

    override fun getItemCount(): Int {
        return usersList.size
    }


}