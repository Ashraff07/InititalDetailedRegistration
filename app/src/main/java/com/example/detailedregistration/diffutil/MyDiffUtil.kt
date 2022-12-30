package com.example.detailedregistration.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.detailedregistration.model.Users

class MyDiffUtil(private val oldList: List<Users>, private val newList: List<Users>) :
    DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }


    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username
    }


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].username != newList[newItemPosition].username -> {
                false
            }
            else -> true

        }

    }
}