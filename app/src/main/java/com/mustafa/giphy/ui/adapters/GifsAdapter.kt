package com.mustafa.giphy.ui.adapters

import androidx.databinding.ViewDataBinding
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.ItemGifCardBinding
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.ui.base.BaseAdapter
import com.mustafa.giphy.utilities.animateClick
import com.mustafa.giphy.utilities.glide


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.ui.adapter
 * Date: 8/21/2021
 */


class GifsAdapter(private val clickListener: AdapterClickListener<Data>) : BaseAdapter<Data>(R.layout.item_gif_card) {

    override fun bind(item: Data, binding: ViewDataBinding, position: Int) {
        binding as ItemGifCardBinding
        binding.apply {
            imageView.glide(item.images?.previewGif?.url)

            if (item.isFavourite) favouriteImageView.setImageResource(R.drawable.ic_baseline_favorite)
            else favouriteImageView.setImageResource(R.drawable.ic_outline_favorite)

            favouriteImageView.setOnClickListener {
                clickListener.onItemClick(item, position)
                favouriteImageView.animateClick()

                if (item.isFavourite) favouriteImageView.setImageResource(R.drawable.ic_outline_favorite)
                else favouriteImageView.setImageResource(R.drawable.ic_baseline_favorite)
                item.isFavourite = !item.isFavourite
            }
        }
    }
}