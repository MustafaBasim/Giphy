package com.mustafa.giphy.utilities

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mustafa.giphy.R
import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.DataResponse
import com.mustafa.giphy.ui.adapters.GifsAdapter

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.utilities
 * Date: 8/21/2021
 */

fun <T : ViewDataBinding> ViewGroup.inflateBinding(layout: Int): T {
    return DataBindingUtil.inflate(LayoutInflater.from(this.context), layout, this, true)
}

fun View.animateClick() {
    this.animate().scaleX(0.8F).scaleY(0.8F).setDuration(150).withEndAction {
        this.animate().scaleX(1.0F).scaleY(1.0F).duration = 150
    }
}

fun View.gone() {
    if (this.visibility != View.GONE) this.visibility = View.GONE
}

fun View.visible() {
    if (this.visibility != View.VISIBLE) this.visibility = View.VISIBLE
}

fun View.invisible() {
    if (this.visibility != View.INVISIBLE) this.visibility = View.INVISIBLE
}

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun ImageView.glide(url: String?, placeholder: String? = null, didLoad: () -> Unit = {}) {
    if (isValidContextForGlide(this.context)) {
        val requestBuilder = Glide.with(this.context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    didLoad()
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }
            })
            .placeholder(R.drawable.ic_gif_placeholder)
            .error(R.drawable.ic_broken_gif)
            .thumbnail(Glide.with(context).load(placeholder))
        requestBuilder.into(this)
    }
}

fun isValidContextForGlide(context: Context?): Boolean {
    if (context == null) {
        return false
    }
    if (context is Activity) {
        val activity = context as Activity?
        if (activity!!.isDestroyed || activity.isFinishing) {
            return false
        }
    }
    return true
}

fun RecyclerView.setup(
    adapter: RecyclerView.Adapter<GifsAdapter.Holder>,
    isGrid: Boolean = false,
    columns: Int = 2,
    isHorizontal: Boolean = false,
    hasFixedSize: Boolean = true
): RecyclerView.LayoutManager {
    adapter.setHasStableIds(true)
    this.adapter = adapter
    this.setHasFixedSize(hasFixedSize)
    this.setItemViewCacheSize(20)
    val layoutManager = when {
        isGrid -> GridLayoutManager(this.context, columns)
        isHorizontal -> LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        else -> LinearLayoutManager(this.context)
    }
    this.layoutManager = layoutManager
    return layoutManager
}


inline fun <reified T> Results<T>.doIfFailure(callback: (error: DataResponse) -> Unit) {
    if (this is Results.Error) {
        callback(error)
    }
}

inline fun <reified T> Results<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this is Results.Success) {
        callback(data)
    }
}

inline fun <reified T> Results<T>.doIfLoading(callback: () -> Unit) {
    if (this is Results.Loading) {
        callback()
    }
}

fun <T> MutableLiveData<Results<T>>.loading() {
    postValue(Results.Loading)
}