package com.mustafa.giphy.ui.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.FragmentSearchBinding
import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.ui.adapters.GifsAdapter
import com.mustafa.giphy.ui.base.BaseAdapter
import com.mustafa.giphy.utilities.*
import com.paginate.Paginate

class SearchFragment : Fragment(), BaseAdapter.AdapterClickListener<Data>, Paginate.Callbacks {

    private val searchViewModel: SearchViewModel by viewModels()
    private val gifsAdapter = GifsAdapter(this)
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        pageViewModel = ViewModelProvider(this).get(SearchViewModel::class.java).apply {
////            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
////        }
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

//        pageViewModel.text.observe(viewLifecycleOwner, Observer {
//            binding.sectionLabel.text = it
//        })

        with(binding) {

            searchViewModel.gifsData.observe(viewLifecycleOwner, {
                it.apply {
                    doIfLoading {
//                        scrollView.gone()
//                        loadingView.loading()
                    }

                    doIfSuccess { gifsData ->
//                        categoriesResponse = categories
//                        scrollView.visible()
//                        loadingView.finished()
                        totalPages = gifsData.pagination?.totalCount ?: 0
                        Log.d("ERROR", "Page = ${gifsData.pagination?.offset}")
                        gifsData.data?.let { it1 ->
                            gifsAdapter.addAll(it1)
//                            if (gifsData.pagination?.offset == 0) gifsAdapter.addAll(it1)
//                            else gifsAdapter.addAllAtBottom(it1)

                            it1.forEach {
                                Log.d("ERROR", "title = ${it.title}")
                            }
                        }
                    }

                    doIfFailure { error ->
//                        loadingView.error(error.message)
                    }
                }
            })

            gifsRecyclerView.setup(adapter = gifsAdapter, isGrid = true, columns = 2)
//            gifsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply { // TODO StaggeredGridLayoutManager
//                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
//            }
            searchViewModel.search("test", currentPage)

//            Paginate.with(gifsRecyclerView, this@SearchFragment)
//                .setLoadingTriggerThreshold(4)
//                .addLoadingListItem(true)
//                .build()
        }


        return binding.root
    }


    override fun onItemClick(data: Data, position: Int) {
        currentPage += Constants.PAGE_LIMIT
        searchViewModel.search("test", currentPage)
//        Log.d("ERROR", " searchViewModel.gifsData.value = ${searchViewModel.gifsData.value} ")
    }

//    companion object {
//        private const val ARG_SECTION_NUMBER = "section_number"
//
//        @JvmStatic
//        fun newInstance(sectionNumber: Int): SearchFragment {
//            return SearchFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_SECTION_NUMBER, sectionNumber)
//                }
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private var currentPage = 0
    private var totalPages = 1

    override fun onLoadMore() {
        currentPage++
        searchViewModel.getTrending(currentPage)
    }

    override fun isLoading(): Boolean = searchViewModel.gifsData.value is Results.Loading

    override fun hasLoadedAllItems(): Boolean = currentPage >= totalPages
}

