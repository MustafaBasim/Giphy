package com.mustafa.giphy.utilities

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.mustafa.giphy.ui.custom_views.ItemLoadingView
import com.mustafa.giphy.ui.fragments.search.SearchViewModel
import com.paginate.recycler.LoadingListItemCreator

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.utilities
 * Date: 8/22/2021
 */

class CustomLoadingListItemCreator(private val viewMode: SearchViewModel, private val viewLifecycleOwner: LifecycleOwner, private val retry: () -> Unit) : LoadingListItemCreator {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = ItemLoadingView(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val itemLoadingView = holder?.itemView
        if (itemLoadingView is ItemLoadingView) {
            itemLoadingView.setup {
                retry()
            }
            viewMode.gifsData.observe(viewLifecycleOwner, { results ->
                results.apply {
                    doIfLoading {
                        itemLoadingView.loading()
                    }
                    doIfSuccess {
                        itemLoadingView.finished()
                    }
                    doIfFailure {
                        itemLoadingView.error(it.meta?.msg)
                    }
                }
            })
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}