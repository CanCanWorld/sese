package com.zrq.sese.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.ref.WeakReference

abstract class BaseVmFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[providedViewModel()]
        binding = providedViewBinding(inflater, container)
        viewModel.context = WeakReference(initViewModel())
        initData()
        initEvent()
        return binding.root
    }

    abstract fun providedViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun providedViewModel(): Class<VM>

    abstract fun initViewModel(): Context

    abstract fun initData()

    abstract fun initEvent()

}
