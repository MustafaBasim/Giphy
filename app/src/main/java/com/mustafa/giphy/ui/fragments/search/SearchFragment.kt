package com.mustafa.giphy.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.FragmentSearchBinding
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.ui.activities.main.MainViewModel
import com.mustafa.giphy.ui.adapters.GifsAdapter
import com.mustafa.giphy.utilities.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), GifsAdapter.AdapterClickListener, ScrollPaginationListener.PaginationListener {

    private val viewModel: SearchViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.
    private val gifsAdapter = GifsAdapter(this)
    private val spanCount = 2

    private lateinit var pagination: ScrollPaginationListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        with(binding) {
            setupRecyclerView()

            swipeRefreshLayout.setOnRefreshListener {
                pagination.reset()
                gifsAdapter.clear()
                viewModel.getGifs(resetPage = true)
            }

            loadingView.setupRetryButtonClick {
                pagination.reset()
                viewModel.getGifs(resetPage = true)
            }

            setupObserves()
            viewModel.getGifs()

            return root
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            // TODO Future feature to make each item fits it's dimensions StaggeredGridLayoutManager
//            gifsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            gifsRecyclerView.setHasFixedSize(true)
            gifsRecyclerView.adapter = gifsAdapter

            val layoutManager = GridLayoutManager(context, spanCount)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (isLastRowAndShouldBeCentered(position)) spanCount else 1
                }
            }
            gifsRecyclerView.layoutManager = layoutManager

            pagination = ScrollPaginationListener(recyclerView = gifsRecyclerView, this@SearchFragment)
        }
    }

    private fun isLastRowAndShouldBeCentered(position: Int): Boolean {
        return if (pagination.isLoading) {
            position == gifsAdapter.size() - 1
        } else false
    }

    private fun setupObserves() {
        with(binding) {
            viewModel.gifsData.observe(viewLifecycleOwner, {
                it.apply {
                    doIfLoading {
                        if (pagination.isLoading) {
                            gifsAdapter.addLoading()
                        } else {
                            loadingView.loading()
                            swipeRefreshLayout.gone()
                        }
                    }

                    doIfSuccess { gifsData ->
                        if (!pagination.isLoading) {
                            if (gifsData.data.isNullOrEmpty()) {
                                swipeRefreshLayout.gone()
                                loadingView.noResult()
                            } else {
                                swipeRefreshLayout.visible()
                                loadingView.finished()
                            }
                        } else {
                            gifsAdapter.removeLoading()
                            pagination.finished(isLastPage = gifsData.data.isNullOrEmpty())
                        }
                        swipeRefreshLayout.isRefreshing = false

                        gifsData.data?.let { nonNullData ->
                            if (gifsData.pagination?.offset == 0) gifsAdapter.addAll(nonNullData)
                            else gifsAdapter.addAllAtBottom(nonNullData)
                        }
                    }

                    doIfFailure { error ->
                        binding.swipeRefreshLayout.isRefreshing = false

                        if (!pagination.isLoading) {
                            loadingView.error(error.message)
                            swipeRefreshLayout.gone()
                        } else {
                            gifsAdapter.setLoadingError(error.message)
                        }
                    }
                }
            })
        }

        mainViewModel.searchQuery.observe(viewLifecycleOwner, { searchQuery ->
            gifsAdapter.clear()
            pagination.reset()
            viewModel.setSearchQuery(searchQuery)
        })

        mainViewModel.removeFromFavourite.observe(viewLifecycleOwner, { removedData ->
            gifsAdapter.removeFromFavourite(removedData)
        })
    }

    override fun onFavouriteClicked(data: Data, position: Int) {
        if (data.isFavourite) {
            mainViewModel.removeFromFavourite(data, notifyObservers = false)
        } else {
            mainViewModel.addToFavourite(data)
        }
    }

    override fun onPageRetryClicked() {
        viewModel.getGifs()
    }

    override fun onLoadMore(nextOffset: Int) {
        viewModel.currentOffset = nextOffset
        viewModel.getGifs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

