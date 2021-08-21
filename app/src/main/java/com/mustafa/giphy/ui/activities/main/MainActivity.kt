package com.mustafa.giphy.ui.activities.main

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mustafa.giphy.R
import com.mustafa.giphy.databinding.ActivityMainBinding
import com.mustafa.giphy.ui.adapters.SectionsPagerAdapter
import com.mustafa.giphy.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_search)
                else -> tab.text = getString(R.string.tab_favourite)
            }
        }.attach()
    }
}