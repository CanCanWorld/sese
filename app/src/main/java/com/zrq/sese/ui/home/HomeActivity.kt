package com.zrq.sese.ui.home

import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.zrq.sese.R
import com.zrq.sese.adapter.HomeAdapter
import com.zrq.sese.base.BaseActivity
import com.zrq.sese.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private lateinit var adapter: HomeAdapter

    override fun initData() {
        adapter = HomeAdapter(this)
        binding.viewPager.adapter = adapter
    }

    override fun initEvent() {

        binding.apply {

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    bottomNavigation.selectedItemId = when (position) {
                        0 -> R.id.item_home
                        1 -> R.id.item_my
                        else -> R.id.item_my
                    }
                    super.onPageSelected(position)
                }

            })

            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_home -> {
                        viewPager.currentItem = 0
                        true
                    }
                    R.id.item_my -> {
                        viewPager.currentItem = 1
                        true
                    }
                    else -> false

                }
            }
        }
    }

    override fun providedViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}