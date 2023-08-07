package com.zrq.sese.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = providedViewBinding()
        setContentView(binding.root)
        initData()
        initEvent()
    }

    protected abstract fun initData()

    protected abstract fun initEvent()

    abstract fun providedViewBinding(): VB
}