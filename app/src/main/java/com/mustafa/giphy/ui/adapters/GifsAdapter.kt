package com.mustafa.giphy.ui.adapters

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
import android.widget.ProgressBar
import androidx.core.view.children
import androidx.databinding.ViewDataBinding
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.ItemGifCardBinding
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.ui.base.BaseAdapter
import com.mustafa.giphy.utilities.animateClick
import com.mustafa.giphy.utilities.glide
import com.mustafa.giphy.utilities.gone
import com.stfalcon.imageviewer.StfalconImageViewer


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.ui.adapter
 * Date: 8/21/2021
 */


class GifsAdapter(private val clickListener: AdapterClickListener<Data>) : BaseAdapter<Data>(layoutId = R.layout.item_gif_card) {

    override fun bind(item: Data, binding: ViewDataBinding, position: Int) {
        binding as ItemGifCardBinding
        binding.apply {
            var previewDrawable: Drawable? = null
            imageView.glide(url = item.images?.previewGif?.url, didLoad = { drawableResource ->
                previewDrawable = drawableResource?.constantState?.newDrawable()?.mutate()
            })

            titleTextView.text = item.title

            if (item.isFavourite) {
                favouriteImageView.setImageResource(R.drawable.ic_round_favorite_24)
            } else {
                favouriteImageView.setImageResource(R.drawable.ic_round_favorite_border_24)
            }

            cardView.setOnClickListener {
                openImage(imageView, previewDrawable, item.images?.original?.url)
            }

            favouriteImageView.setOnClickListener {
                clickListener.onItemClick(item, position)
                favouriteImageView.animateClick()

                if (item.isFavourite) {
                    favouriteImageView.setImageResource(R.drawable.ic_round_favorite_border_24)
                } else {
                    favouriteImageView.setImageResource(R.drawable.ic_round_favorite_24)
                }
                item.isFavourite = !item.isFavourite
            }
        }
    }

    private fun openImage(imageView: ImageView, previewDrawable: Drawable?, originalUrl: String?) {
        val progressBarView = LinearLayout(imageView.context).apply {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            setBackgroundResource(android.R.color.transparent)
            this.addView(ProgressBar(imageView.context).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER
                    setBackgroundResource(android.R.color.transparent)
                }
            })
        }

        StfalconImageViewer.Builder(imageView.context, listOf(originalUrl)) { view, image ->
            view.glide(url = image, placeholder = previewDrawable, didLoad = {
                progressBarView.children.firstOrNull()?.gone()
            })
        }
            .withBackgroundColorResource(android.R.color.transparent)
            .allowZooming(false)
            .withHiddenStatusBar(false)
            .withTransitionFrom(imageView)
            .withOverlayView(progressBarView)
            .show()
    }
}