package com.mustafa.giphy.utilities

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.DataResponse
import com.mustafa.giphy.ui.base.BaseAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.utilities
 * Date: 8/21/2021
 */

fun <T : ViewDataBinding> ViewGroup.inflateBinding(layout: Int): T {
//    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//    return DataBindingUtil.inflate(inflater, layout,this,true)
    return DataBindingUtil.inflate(LayoutInflater.from(this.context), layout, this, true)
}

fun EditText.onTextChange(delay: Long = 300, afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        private var timer: Timer = Timer()
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            timer.cancel()
            timer = Timer()
            timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        afterTextChanged(s.toString())
                    }
                },
                delay
            )
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    })
}

fun Int.toIQD(): String = if (isEng()) "${this.format()} IQD" else "${this.format()} د.ع"
fun Int.format(): String = String.format("%,d", this) //       2020-06-08 07:00:00
fun Long.formatDateTime(): String =
    SimpleDateFormat("yyyy/M/d hh:mm a", Locale.ENGLISH).format(Date(this))

fun Long.formatDateTimeEng(): String =
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Date(this))

fun Long.formatDateDash(): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date(this))

fun Long.formatDate(): String = DateFormat.format("yyyy/M/d", Date(this)).toString()
fun Long.formatTime(): String = DateFormat.format("hh:mm aa", Date(this)).toString()
fun Long.formatDay(): String = DateFormat.format("d", Date(this)).toString()

fun View.animateClick() {
    this.animate().scaleX(0.8F).scaleY(0.8F).setDuration(150).withEndAction {
        this.animate().scaleX(1.0F).scaleY(1.0F).duration = 150
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Int.toDp(res: Resources): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        res.displayMetrics
    ).toInt()
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

fun View.animateGone() {
    this.animate().alpha(0.0f).withEndAction { this.gone() }
}

fun isEng(): Boolean = Locale.getDefault().language == Locale("en").language

fun ImageView.glide(url: String?, placeholderId: Int = 0, didLoad: () -> Unit = {}) {
    if (isValidContextForGlide(this.context)) {
        val requestBuilder = Glide.with(this.context)
            .load(url)
//                .error(mDefaultCardImage)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    didLoad()
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    Log.d("ERROR", " e = ${e?.message} ")
                    return false
                }
            })
        if (placeholderId != 0) requestBuilder.placeholder(placeholderId)
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

fun <T> RecyclerView.setup(
    adapter: RecyclerView.Adapter<BaseAdapter<T>.Holder>,
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

fun tryCatch(catchBlock: () -> Unit = {}, tryBlock: () -> Unit) {
    try {
        tryBlock()
    } catch (e: Exception) {
        catchBlock()
    }
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