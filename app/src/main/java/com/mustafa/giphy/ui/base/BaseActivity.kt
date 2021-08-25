package com.mustafa.giphy.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.ui.base
 * Date: 8/21/2021
 */

@SuppressLint("Registered")
abstract class BaseActivity<VDB : ViewDataBinding>(private val layoutId: Int?) : AppCompatActivity() {
    
    protected lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutId != null) binding = DataBindingUtil.setContentView(this, layoutId)
        onCreate()
    }

    abstract fun onCreate()
}
