package com.mustafa.giphy.ui.custom_views

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.LoadingViewBinding
import com.mustafa.giphy.utilities.Constants
import com.mustafa.giphy.utilities.gone
import com.mustafa.giphy.utilities.inflateBinding
import com.mustafa.giphy.utilities.visible


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: Giphy
 * Package: com.mustafa.giphy.ui.custom_views
 * Date: 8/22/2021
 */

class LoadingView(context: Context, attributeSet: AttributeSet? = null) : ConstraintLayout(context, attributeSet) {

    val binding: LoadingViewBinding = inflateBinding(R.layout.loading_view)

    init {
        binding.apply {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LoadingView)
            when (typedArray.getInteger(R.styleable.LoadingView_initialState, 0)) {
                0 -> loading()
                1 -> error()
                2 -> finished()
            }
            typedArray.recycle()
        }
    }

    fun setupRetryButtonClick(click: () -> Unit) {
        binding.retryButton.setOnClickListener {
            click()
        }
    }

    fun loading() {
        binding.apply {
            visible()
            lottieAnimationView.visible()
            lottieAnimationView.setAnimation(R.raw.lottie_animation_loading_cat)
            lottieAnimationView.playAnimation()
            retryButton.gone()
            messageTextView.gone()
        }
    }

    fun noResult(msg: String = context.getString(R.string.no_result_message)) {
        binding.apply {
            visible()
            lottieAnimationView.visible()
            lottieAnimationView.setAnimation(R.raw.lottie_animation_no_results)
            lottieAnimationView.playAnimation()
            retryButton.gone()
            messageTextView.visible()
            messageTextView.text = msg
        }
    }

    fun error(msg: String? = context.getString(R.string.error_message)) {
        binding.apply {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.apply {
                    visible()
                    lottieAnimationView.setAnimation(R.raw.lottie_animation_no_internet_animation)
                    lottieAnimationView.playAnimation()
                    retryButton.visible()
                    messageTextView.visible()
                    messageTextView.text = if (msg != Constants.API_NO_INTERNET && msg != null) msg else context.getString(R.string.error_message)
                }
            }, 1000)
        }
    }

    fun finished() = gone()

}