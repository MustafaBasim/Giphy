package com.mustafa.giphy.ui.base


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mustafa.giphy.utilities.DiffUtilCallback

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.ui.base
 * Date: 8/21/2021
 */

abstract class BaseAdapter<T>(
    private val layoutId: Int,
    private val hasSecondLayout: Boolean = false,
    private val secondLayoutId: Int = 0,
    private val loadingLayoutId: Int = 0
) :
    RecyclerView.Adapter<BaseAdapter<T>.Holder>() {


    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding: ViewDataBinding = when (viewType) {
            VIEW_TYPE_LOADING -> DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                loadingLayoutId,
                parent,
                false
            )
            VIEW_TYPE_NORMAL_SECOND -> DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                secondLayoutId,
                parent,
                false
            )
            else -> DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        }
        onCreateViewHolder(binding)
        return Holder(binding, viewType)
    }

    open fun onCreateViewHolder(binding: ViewDataBinding) {}

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == size() - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else if (hasSecondLayout) {
            if (position % 2 == 0) VIEW_TYPE_NORMAL
            else VIEW_TYPE_NORMAL_SECOND
        } else VIEW_TYPE_NORMAL
    }

    override fun getItemCount(): Int {
        return arrayList.count()
    }

    override fun getItemId(position: Int): Long {
        return arrayList[position].hashCode().toLong()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position], position)
    }

    abstract fun bind(item: T, binding: ViewDataBinding, position: Int)
    open fun secondBind(item: T, binding: ViewDataBinding, position: Int) {}

    open fun bindLoading(binding: ViewDataBinding, position: Int) {}

    inner class Holder(
        private val binding: ViewDataBinding,
        private val viewType: Int = VIEW_TYPE_NORMAL
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T, position: Int) {
            when (viewType) {
                VIEW_TYPE_NORMAL -> bind(item, binding, position)
                VIEW_TYPE_NORMAL_SECOND -> secondBind(item, binding, position)
                VIEW_TYPE_LOADING -> bindLoading(binding, position)
            }
        }
    }

    private val arrayList = ArrayList<T>()

    open fun add(item: T, notify: Boolean = true) {
        arrayList.add(item)
        if (notify) notifyItemInserted(arrayList.size - 1)
    }

    open fun add(item: T, index: Int, notify: Boolean = true) {
        if (index > -1 && index < arrayList.size) arrayList.add(index, item)
        else arrayList.add(item)
        if (notify) notifyItemInserted(index)
    }

    open fun addAll(items: ArrayList<T>, clearFirst: Boolean = false) {
        DiffUtil.calculateDiff(DiffUtilCallback(arrayList, items)).run {
            if (!clearFirst) arrayList.clear()
            arrayList.addAll(items)
            dispatchUpdatesTo(this@BaseAdapter)
        }
    }

    open fun addAllAtBottom(items: ArrayList<T>, notify: Boolean = true) {
        val size = size()
        arrayList.addAll(items)
        if (notify) notifyItemRangeInserted(size, items.size)
    }

    open fun remove(item: T, notify: Boolean = true) {
        val position = arrayList.indexOf(item)
        if (position > -1) {
            arrayList.removeAt(position)
            if (notify) notifyItemRemoved(position)
        }
    }

    open fun clear(notify: Boolean = true) {
        arrayList.clear()
        if (notify) notifyDataSetChanged()
    }

    open fun size(): Int = arrayList.size

    open fun isEmpty(): Boolean = arrayList.isEmpty()

    open fun contains(item: T): Boolean = arrayList.contains(item)

    open fun getItem(position: Int): T? {
        return if (position > -1 && position < arrayList.size) arrayList[position]
        else null
    }

    open fun getAll(): ArrayList<T> {
        return arrayList
    }

    fun addLoading(item: T) {
        isLoaderVisible = true
        add(item)
    }

    fun removeLoading(notify: Boolean = true) {
        isLoaderVisible = false
        val position = arrayList.size - 1
        val item = getItem(position)
        if (item != null) {
            arrayList.removeAt(position)
            if (notify) notifyItemRemoved(position)
        }
    }

    interface AdapterClickListener<T> {
        fun onItemClick(data: T, position: Int)
    }

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_NORMAL = 1
        const val VIEW_TYPE_NORMAL_SECOND = 2
    }
}