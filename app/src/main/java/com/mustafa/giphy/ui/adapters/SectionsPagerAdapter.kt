package com.mustafa.giphy.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mustafa.giphy.ui.fragments.favourite.FavouriteFragment
import com.mustafa.giphy.ui.fragments.search.SearchFragment

class SectionsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> SearchFragment()
        else -> FavouriteFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}