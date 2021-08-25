package com.mustafa.giphy.ui.activities.main

import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.ActivityMainBinding
import com.mustafa.giphy.ui.adapters.SectionsPagerAdapter
import com.mustafa.giphy.ui.base.BaseActivity
import com.mustafa.giphy.utilities.SnackbarUndoListener
import com.mustafa.giphy.utilities.hideKeyboard
import com.mustafa.giphy.utilities.invisible
import com.mustafa.giphy.utilities.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(layoutId = R.layout.activity_main), SnackbarUndoListener {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate() {
        setupViewPager()
        setupSearchView()
        toolbarHandlingWhenTabChanged()
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.id = 0
                    tab.text = getString(R.string.tab_trending)
                    tab.setIcon(R.drawable.ic_trending)
                }
                else -> {
                    tab.id = 1
                    tab.text = getString(R.string.tab_favourite)
                    tab.setIcon(R.drawable.ic_round_favorite)
                }
            }
        }.attach()
    }

    private fun toolbarHandlingWhenTabChanged() {
        with(binding) {
            searchView.setOnSearchClickListener {
                title.invisible()
            }
            searchView.setOnCloseListener {
                title.visible()
                false
            }
            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.id) {
                        0 -> {
                            searchView.visible()
                            if (searchView.query.isNullOrEmpty()) {
                                title.visible()
                            } else {
                                title.invisible()
                            }
                            searchView.isIconified = searchView.query.isNullOrEmpty()
                            searchView.hideKeyboard()
                        }
                        1 -> {
                            searchView.invisible()
                            title.visible()
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var timer: Timer = Timer()
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setSearchQuery(query ?: "")
                binding.root.hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() = viewModel.setSearchQuery(newText ?: "")
                }, 600)
                return true
            }
        })
    }

    override fun snackbarUndoClick() { // TODO : Future feature, add undo snackbar when Gif removed from favourite
//        val removeFromFavouriteSnackbar = Snackbar.make(binding.root, getString(R.string.removed_from_favourite), Snackbar.LENGTH_LONG)
//        removeFromFavouriteSnackbar.setAction(getString(R.string.undo), this)
//        removeFromFavouriteSnackbar.addCallback( object : Snackbar.Callback() {
//            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
//                super.onDismissed(transientBottomBar, event)
//
//            }
//        })
//        removeFromFavouriteSnackbar.show()
    }
}