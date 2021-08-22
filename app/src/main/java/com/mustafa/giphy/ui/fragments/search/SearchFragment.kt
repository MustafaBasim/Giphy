package com.mustafa.giphy.ui.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.FragmentSearchBinding
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.ui.activities.main.MainViewModel
import com.mustafa.giphy.ui.adapters.GifsAdapter
import com.mustafa.giphy.ui.base.BaseAdapter
import com.mustafa.giphy.utilities.*
import com.paginate.Paginate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), BaseAdapter.AdapterClickListener<Data>, Paginate.Callbacks {

    private val viewModel: SearchViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private val gifsAdapter = GifsAdapter(this)
    private var paginate: Paginate? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        with(binding) {
            gifsRecyclerView.setup(adapter = gifsAdapter, isGrid = true, columns = 2)
//            gifsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) // TODO StaggeredGridLayoutManager

//            paginate = Paginate.with(gifsRecyclerView, this@SearchFragment)
//                .setLoadingTriggerThreshold(4)
//                .addLoadingListItem(true)
//                .setLoadingListItemCreator(CustomLoadingListItemCreator(viewModel, viewLifecycleOwner) {
//                    viewModel.getGifs()
//                })
//                .build()


            setupObserves()

            viewModel.getGifs()

            return root
        }
    }

    private fun setupObserves() {
        viewModel.gifsData.observe(viewLifecycleOwner, {
            it.apply {
                doIfLoading {
//                        scrollView.gone()
//                        loadingView.loading()
                }

                doIfSuccess { gifsData ->
//                        categoriesResponse = categories
//                        scrollView.visible()
//                        loadingView.finished()
                    viewModel.totalCount = gifsData.pagination?.totalCount ?: 0
                    Log.d("ERROR", "Page = ${gifsData.pagination?.offset}")
                    gifsData.data?.let { it1 ->
                        if (gifsData.pagination?.offset == 0) gifsAdapter.addAll(it1)
                        else gifsAdapter.addAllAtBottom(it1)
                    }
                }

                doIfFailure { error ->
//                        loadingView.error(error.message)
                }
            }
        })

        mainViewModel.searchQuery.observe(viewLifecycleOwner, { searchQuery ->
            viewModel.setSearchQuery(searchQuery)
        })

        mainViewModel.removeFromFavourite.observe(viewLifecycleOwner, { removedData ->
            gifsAdapter.getAll().find { it.id == removedData.id }?.let {
                it.isFavourite = false
                gifsAdapter.notifyItemChanged(gifsAdapter.getAll().indexOf(it))
            }
        })
    }

    override fun onLoadMore() {
        Log.d("ERROR", " onLoadMore ")
        viewModel.currentOffset += Constants.PAGE_LIMIT
        viewModel.getGifs()
    }

    override fun onItemClick(data: Data, position: Int) {
        if (data.isFavourite) {
            mainViewModel.removeFromFavourite(data, notifyObservers = false)
        } else {
            mainViewModel.addToFavourite(data)
        }
    }

    override fun isLoading(): Boolean = viewModel.isLoading()

    override fun hasLoadedAllItems(): Boolean = viewModel.hasLoadedAllItems()

    override fun onDestroyView() {
        super.onDestroyView()
        paginate?.unbind()
        paginate = null
        _binding = null
    }
}

