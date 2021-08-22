package com.mustafa.giphy.ui.activities.main

import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.ActivityMainBinding
import com.mustafa.giphy.ui.adapters.SectionsPagerAdapter
import com.mustafa.giphy.ui.base.BaseActivity
import com.mustafa.giphy.utilities.SnackbarUndoListener
import com.mustafa.giphy.utilities.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(layoutId = R.layout.activity_main), SnackbarUndoListener {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.tab_search)
                    tab.setIcon(R.drawable.ic_round_search_24)
                }
                else -> {
                    tab.text = getString(R.string.tab_favourite)
                    tab.setIcon(R.drawable.ic_round_favorite_24)
                }
            }
        }.attach()

        setupSearchView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.removeFromFavourite.observe(this, {

        })
    }

    override fun snackbarUndoClick() {
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
}