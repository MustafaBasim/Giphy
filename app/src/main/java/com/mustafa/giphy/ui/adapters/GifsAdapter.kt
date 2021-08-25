package com.mustafa.giphy.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.ItemGifCardBinding
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.ui.custom_views.ImageViewerOverlayView
import com.mustafa.giphy.ui.custom_views.RecyclerItemLoadingView
import com.mustafa.giphy.utilities.*
import com.stfalcon.imageviewer.StfalconImageViewer


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.ui.adapter
 * Date: 8/21/2021
 */


class GifsAdapter(private val clickListener: AdapterClickListener) : RecyclerView.Adapter<GifsAdapter.Holder>() {

    private var isLoaderVisible = false
    private val arrayList = ArrayList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsAdapter.Holder {
        val view: View = when (viewType) {
            VIEW_TYPE_LOADING -> RecyclerItemLoadingView(parent.context)
            else -> LayoutInflater.from(parent.context).inflate(
                R.layout.item_gif_card,
                parent,
                false
            )
        }
        return Holder(view, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible && position == size() - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
    }

    override fun getItemCount(): Int = arrayList.count()

    override fun getItemId(position: Int): Long = arrayList[position].hashCode().toLong()

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position], position)
    }

    inner class Holder(
        private val view: View,
        private val viewType: Int = VIEW_TYPE_NORMAL
    ) : RecyclerView.ViewHolder(view) {
        fun bind(item: Data, position: Int) {
            when (viewType) {
                VIEW_TYPE_NORMAL -> bindData(item, view, position)
                VIEW_TYPE_LOADING -> bindLoading(item, view)
            }
        }
    }

    fun bindData(item: Data, view: View, position: Int) {
        DataBindingUtil.bind<ItemGifCardBinding>(view)?.apply {
            imageView.glide(url = item.images?.previewGif?.url)
            if (item.title.isNullOrBlank()) {
                titleTextView.invisible()
            } else {
                titleTextView.visible()
                titleTextView.text = item.title
            }

            if (item.isFavourite) {
                favouriteImageView.setImageResource(R.drawable.ic_round_favorite)
            } else {
                favouriteImageView.setImageResource(R.drawable.ic_round_favorite_border)
            }

            cardView.setOnClickListener {
                openImage(imageView, item)
            }

            favouriteImageView.setOnClickListener {
                clickListener.onFavouriteClicked(item, position)
                favouriteImageView.animateClick()

                if (item.isFavourite) {
                    favouriteImageView.setImageResource(R.drawable.ic_round_favorite_border)
                } else {
                    favouriteImageView.setImageResource(R.drawable.ic_round_favorite)
                }
                item.isFavourite = !item.isFavourite
            }
        }
    }

    private fun openImage(imageView: ImageView, item: Data) {
        val imageViewerOverlayView = ImageViewerOverlayView(imageView.context)

        imageViewerOverlayView.setTitle(item.title)

        StfalconImageViewer.Builder(imageView.context, listOf(item.images?.original?.url)) { view, image ->
            view.glide(url = image, placeholder = item.images?.previewGif?.url, didLoad = {
                imageViewerOverlayView.hideProgress()
            })
        }
            .withBackgroundColorResource(android.R.color.transparent)
            .allowZooming(false)
            .withHiddenStatusBar(false)
            .withTransitionFrom(imageView)
            .withOverlayView(imageViewerOverlayView)
            .show()
    }

    fun removeFromFavourite(item: Data) {
        arrayList.find { it.id == item.id }?.let {
            it.isFavourite = false
            notifyItemChanged(arrayList.indexOf(it))
        }
    }

    fun addLoading() {
        if (!isLoaderVisible) {
            isLoaderVisible = true
            arrayList.add(Data(type = TYPE_LOADING))
            notifyItemInserted(arrayList.size - 1)
        }
    }

    fun removeLoading() {
        isLoaderVisible = false
        arrayList.removeAt(arrayList.size - 1)
        notifyItemRemoved(arrayList.size - 1)
    }

    fun setLoadingError(message: String?) {
        arrayList.lastOrNull {
            if (it.type == TYPE_LOADING) {
                it.type = TYPE_ERROR
                it.title = message
                notifyItemChanged(size() - 1)
            }
            true
        }
    }

    fun bindLoading(item: Data, view: View) {
        (view as? RecyclerItemLoadingView)?.apply {
            retryButtonClicked {
                item.type = TYPE_LOADING
                notifyItemChanged(size() - 1)
                clickListener.onPageRetryClicked()
            }
            when (item.type) {
                TYPE_LOADING -> loading()
                TYPE_ERROR -> error(item.title)
            }
        }
    }

    fun addAll(items: ArrayList<Data>) {
        DiffUtil.calculateDiff(DiffUtilCallback(arrayList, items)).run {
            arrayList.clear()
            arrayList.addAll(items)
            dispatchUpdatesTo(this@GifsAdapter)
        }
    }

    fun addAllAtBottom(items: ArrayList<Data>, notify: Boolean = true) {
        val size = size()
        arrayList.addAll(items)
        if (notify) notifyItemRangeInserted(size, items.size)
    }

    fun size(): Int = arrayList.size

    fun clear() {
        arrayList.clear()
        notifyDataSetChanged()
    }

    interface AdapterClickListener {
        fun onFavouriteClicked(data: Data, position: Int)
        fun onPageRetryClicked() {}
    }

    companion object {
        const val TYPE_LOADING = "TYPE_LOADING"
        const val TYPE_ERROR = "TYPE_ERROR"

        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_NORMAL = 1
    }
}