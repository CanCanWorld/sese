package com.zrq.sese.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = providedViewBinding(inflater, container)
        initData()
        initEvent()
        return binding.root
    }

    abstract fun providedViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun initData()

    abstract fun initEvent()

}
