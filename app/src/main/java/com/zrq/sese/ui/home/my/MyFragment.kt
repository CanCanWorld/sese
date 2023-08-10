package com.zrq.sese.ui.home.my

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zrq.sese.databinding.FragmentMyBinding
import com.zrq.sese.base.BaseVmFragment
import com.zrq.sese.ui.history.HistoryActivity
import com.zrq.sese.ui.love.LoveActivity

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
            startActivity(Intent(requireActivity(), HistoryActivity::class.java))
        }
        binding.llLove.setOnClickListener {
            startActivity(Intent(requireActivity(), LoveActivity::class.java))
        }
        binding.llDownload.setOnClickListener {
        }
    }

}