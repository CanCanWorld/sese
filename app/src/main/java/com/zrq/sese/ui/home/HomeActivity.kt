package com.zrq.sese.ui.home

import android.content.Context
import com.zrq.sese.databinding.ActivityHomeBinding
import com.zrq.sese.base.BaseVmActivity
import com.zrq.webvideo.ui.home.HomeViewModel

class HomeActivity : BaseVmActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun initData() {
    }

    override fun initEvent() {
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