package com.zrq.sese.ui.history

import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.zrq.sese.adapter.HistoryVideoAdapter
import com.zrq.sese.base.BaseVmActivity
import com.zrq.sese.databinding.ActivityHistoryBinding
import com.zrq.sese.db.RoomController
import com.zrq.sese.db.table.HistoryTable
import com.zrq.sese.ui.player.PlayerActivity

class HistoryActivity : BaseVmActivity<ActivityHistoryBinding, HistoryViewModel>() {
    override fun providedViewBinding(): ActivityHistoryBinding {
        return ActivityHistoryBinding.inflate(layoutInflater)
    }

    override fun providedViewModel(): Class<HistoryViewModel> {
        return HistoryViewModel::class.java
    }

    override fun initViewModel(): Context {
        return this
    }

    private val list = mutableListOf<HistoryTable>()

    private lateinit var adapter: HistoryVideoAdapter

    override fun initData() {
        adapter = HistoryVideoAdapter(this, list, { binding, position ->
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
        list.addAll(RoomController.historyDao().queryAll().reversed())
        adapter.notifyDataSetChanged()
    }

    override fun initEvent() {
    }
}