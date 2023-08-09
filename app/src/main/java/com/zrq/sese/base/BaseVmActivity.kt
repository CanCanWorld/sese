package com.zrq.sese.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.ref.WeakReference

abstract class BaseVmActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = providedViewBinding()
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[providedViewModel()]
        viewModel.context = WeakReference(initViewModel())
        initData()
        initEvent()
    }

    abstract fun providedViewBinding(): VB

    abstract fun providedViewModel(): Class<VM>

    abstract fun initViewModel(): Context

    abstract fun initData()

    abstract fun initEvent()
}