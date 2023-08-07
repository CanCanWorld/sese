package com.zrq.sese.ui.player

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zrq.sese.databinding.FragmentPlayerBinding
import com.zrq.sese.base.BaseFragment

class PlayerFragment : BaseFragment<FragmentPlayerBinding>() {
    override fun providedViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentPlayerBinding {
        return FragmentPlayerBinding.inflate(layoutInflater, container, false)
    }

    override fun initData() {
    }



    override fun initEvent() {
    }

    companion object {
        const val TAG = "PlayerFragment"
    }

}