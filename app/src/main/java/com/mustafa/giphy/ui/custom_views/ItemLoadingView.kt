package com.mustafa.giphy.ui.custom_views

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.ItemLoadingBinding
import com.mustafa.giphy.utilities.Constants
import com.mustafa.giphy.utilities.gone
import com.mustafa.giphy.utilities.invisible
import com.mustafa.giphy.utilities.visible

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.ui.custom_views
 * Date: 8/22/2021
 */

class ItemLoadingView(context: Context, attributeSet: AttributeSet? = null) : ConstraintLayout(context, attributeSet) {

    val binding: ItemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.item_loading, this, true)

    fun setup(click: () -> Unit) {
        binding.retryButton.setOnClickListener {
            click()
        }
    }

    fun loading() {
        binding.apply {
            visible()
            progressIndicator.visible()
            retryButton.gone()
            messageTextView.gone()
        }
    }

    fun error(msg: String? = context.getString(R.string.error_message)) {
        binding.apply {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.apply {
                    visible()
                    progressIndicator.gone()
                    retryButton.visible()
                    messageTextView.visible()
                    messageTextView.text = if (msg != Constants.API_NO_INTERNET && msg != null) msg else context.getString(R.string.error_message)
                }
            }, 1000)
        }
    }

    fun finished() {
        binding.apply {
            gone()
        }
    }

}