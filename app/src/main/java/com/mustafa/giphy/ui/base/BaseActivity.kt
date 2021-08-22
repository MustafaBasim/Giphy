package com.mustafa.giphy.ui.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.mustafa.giphy.utilities.Constants


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.ui.base
 * Date: 8/21/2021
 */

@SuppressLint("Registered")
abstract class BaseActivity<VDB : ViewDataBinding>(private val layoutId: Int?) : AppCompatActivity() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Lingver.getInstance().setLocale(this, Hawk.get(Prefs.LANGUAGE, Locale.getDefault().language))
//    }

    protected lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutId != null) binding = DataBindingUtil.setContentView(this, layoutId)
        onCreate()
    }

    abstract fun onCreate()

    fun String.toast(isLong: Boolean = true, gravityTop: Boolean = false) {
        val t = Toast.makeText(
            this@BaseActivity,
            this,
            if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        )
        if (gravityTop) t.setGravity(Gravity.TOP, 0, resources.displayMetrics.heightPixels / 4)
        t.show()
    }

    fun View.snack(text: String, isLong: Boolean = true) {
        Snackbar.make(this, text, if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT)
            .show()
    }

    inline fun <reified T> start(block: Intent.() -> Unit = {}) {
        startActivity(Intent(this, T::class.java).apply(block))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun enableViews(isEnabled: Boolean, vararg views: View) {
        views.forEach {
            it.isEnabled = isEnabled
        }
    }

    fun gone(vararg views: View) {
        views.forEach {
            it.visibility = View.GONE
        }
    }

    fun visible(vararg views: View) {
        views.forEach {
            it.visibility = View.VISIBLE
        }
    }

//    fun errorMsg(msg: String?): String = if (msg != Constants.API_NO_INTERNET && msg != null) msg else getString(R.string.error_message)
}
