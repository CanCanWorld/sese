package com.zrq.sese.ui.download

import android.content.Context
import com.zrq.sese.base.BaseVmActivity
import com.zrq.sese.databinding.ActivityDownloadBinding

class DownloadActivity : BaseVmActivity<ActivityDownloadBinding, DownloadViewModel>() {

    override fun providedViewBinding(): ActivityDownloadBinding {
        return ActivityDownloadBinding.inflate(layoutInflater)
    }

    override fun providedViewModel(): Class<DownloadViewModel> {
        return DownloadViewModel::class.java
    }

    override fun initViewModel(): Context {
        return this
    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}