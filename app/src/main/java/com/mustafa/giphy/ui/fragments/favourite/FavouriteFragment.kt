package com.mustafa.giphy.ui.fragments.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.FragmentFavouriteBinding
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.ui.activities.main.MainViewModel
import com.mustafa.giphy.ui.adapters.GifsAdapter
import com.mustafa.giphy.utilities.ObjectsMapper.toGifData
import com.mustafa.giphy.utilities.gone
import com.mustafa.giphy.utilities.setup
import com.mustafa.giphy.utilities.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment(), GifsAdapter.AdapterClickListener {

    private val viewModel: FavouriteViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentFavouriteBinding? = null
    private val gifsAdapter = GifsAdapter(this)

    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false)
        with(binding) {
            gifsRecyclerView.setup(adapter = gifsAdapter, isGrid = true, columns = 2)
        }

        setupObserves()

        return binding.root
    }

    private fun setupObserves() {
        with(binding) {
            viewModel.favouriteGifsData.observe(viewLifecycleOwner, {
                it.apply {
                    val arrayList = ArrayList<Data>()
                    it.forEach { favouriteGif ->
                        arrayList.add(favouriteGif.toGifData())
                    }

                    if (gifsAdapter.size() < arrayList.size) gifsRecyclerView.smoothScrollToPosition(0)

                    gifsAdapter.addAll(arrayList)

                    if (arrayList.isEmpty()) {
                        lottieAnimationView.visible()
                        noFavouritesMessageTextView.visible()
                    } else {
                        lottieAnimationView.gone()
                        noFavouritesMessageTextView.gone()
                    }
                }
            })
        }
    }

    override fun onFavouriteClicked(data: Data, position: Int) {
        if (data.isFavourite) mainViewModel.removeFromFavourite(data, notifyObservers = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}