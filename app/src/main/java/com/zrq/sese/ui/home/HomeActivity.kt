package com.zrq.sese.ui.home

import android.content.Context
import androidx.navigation.Navigation
import com.zrq.sese.R
import com.zrq.sese.databinding.ActivityHomeBinding
import com.zrq.sese.base.BaseVmActivity

class HomeActivity : BaseVmActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun initData() {
    }

    override fun initEvent() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    Navigation.findNavController(this ,R.id.fragment_container)
                        .navigate(R.id.homeFragment)
                    true
                }
                R.id.item_my -> {
                    Navigation.findNavController(this ,R.id.fragment_container)
                        .navigate(R.id.myFragment)
                    true
                }
                else -> false

            }
        }
    }

    override fun providedViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun initViewModel(): Context {
        return this
    }

    override fun providedViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}