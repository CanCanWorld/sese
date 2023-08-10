package com.zrq.sese.ui.love

import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.zrq.sese.adapter.HistoryVideoAdapter
import com.zrq.sese.adapter.LoveVideoAdapter
import com.zrq.sese.base.BaseVmActivity
import com.zrq.sese.databinding.ActivityHistoryBinding
import com.zrq.sese.databinding.ActivityLoveBinding
import com.zrq.sese.db.MyDatabase
import com.zrq.sese.db.RoomController
import com.zrq.sese.db.table.HistoryTable
import com.zrq.sese.db.table.LoveTable
import com.zrq.sese.ui.player.PlayerActivity

class LoveActivity : BaseVmActivity<ActivityLoveBinding, LoveViewModel>() {
    override fun providedViewBinding(): ActivityLoveBinding {
        return ActivityLoveBinding.inflate(layoutInflater)
    }

    override fun providedViewModel(): Class<LoveViewModel> {
        return LoveViewModel::class.java
    }

    override fun initViewModel(): Context {
        return this
    }

    private val list = mutableListOf<LoveTable>()

    private lateinit var adapter: LoveVideoAdapter

    override fun initData() {
        adapter = LoveVideoAdapter(this, list, { binding, position ->
            val transition = "cover$position"
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.videoView, transition).toBundle()
            startActivity(Intent(this, PlayerActivity::class.java).apply {
                putExtra("video", list[position].toVideoItem())
                binding.videoView.transitionName = transition
                putExtra("transition", transition)
            }, options)
        }, {
            list.forEach { item ->
                item.isPlayer = false
            }
            list[it].isPlayer = true
            adapter.notifyDataSetChanged()
        })
        binding.recyclerView.adapter = adapter

        list.clear()
        list.addAll(RoomController.loveDao().queryAll().reversed())
        adapter.notifyDataSetChanged()
    }

    override fun initEvent() {
    }
}