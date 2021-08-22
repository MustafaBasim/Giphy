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

interface SnackbarUndoListener : View.OnClickListener {
    override fun onClick(v: View) {

    }

    abstract fun snackbarUndoClick()
}