package com.zrq.sese.ui.home.my

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zrq.sese.databinding.FragmentMyBinding
import com.zrq.sese.base.BaseVmFragment

class MyFragment : BaseVmFragment<FragmentMyBinding, MyViewModel>() {
    override fun providedViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMyBinding {
        return FragmentMyBinding.inflate(layoutInflater)
    }

    override fun providedViewModel(): Class<MyViewModel> {
        return MyViewModel::class.java
    }

    override fun initViewModel(): Context {
        return requireActivity()
    }

    override fun initData() {

    }

    override fun initEvent() {
        binding.llHistory.setOnClickListener {

        }
    }

}