package com.mustafa.giphy.utilities

import androidx.recyclerview.widget.DiffUtil
import com.mustafa.giphy.model.data_models.responses.Data


open class DiffUtilCallback(private val oldList: ArrayList<Data>, private val newList: ArrayList<Data>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition].id == oldList[oldItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition] == oldList[oldItemPosition]
}
