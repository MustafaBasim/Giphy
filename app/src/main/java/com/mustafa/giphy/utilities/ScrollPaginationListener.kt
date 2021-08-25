package com.mustafa.giphy.utilities

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.utilities
 * Date: 8/23/2021
 */
class ScrollPaginationListener(recyclerView: RecyclerView, paginationListener: PaginationListener) {

    var currentOffset: Int = 0
    var isLastPage = false
    val pageSize = Constants.PAGE_LIMIT
    var isLoading: Boolean = false

    init {
        val gridLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = gridLayoutManager.childCount
                val totalItemCount = gridLayoutManager.itemCount
                val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= pageSize
                    ) {
                        isLoading = true
                        currentOffset += pageSize
                        paginationListener.onLoadMore(currentOffset)
                    }
                }
            }
        })
    }

    fun finished(isLastPage: Boolean) {
        this.isLastPage = isLastPage
        isLoading = false
    }

    fun reset() {
        isLoading = false
        isLastPage = false
        currentOffset = 0
    }

    interface PaginationListener {
        fun onLoadMore(nextOffset: Int)
    }

}
