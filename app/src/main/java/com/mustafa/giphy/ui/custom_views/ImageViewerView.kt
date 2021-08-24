package com.mustafa.giphy.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.ImageViewerBinding
import com.mustafa.giphy.utilities.gone

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.ui.custom_views
 * Date: 8/23/2021
 */

class ImageViewerView(context: Context, attributeSet: AttributeSet? = null) : ConstraintLayout(context, attributeSet) {

    val binding: ImageViewerBinding = DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.image_viewer, this, true)

    init {
        setBackgroundResource(android.R.color.transparent)
    }

    fun setTitle(title: String?) {
        if (title == null) {
            binding.titleTextView.gone()
        } else {
            binding.titleTextView.text = title
        }
    }

    fun hideProgress() {
        binding.progressIndicator.gone()
    }

}