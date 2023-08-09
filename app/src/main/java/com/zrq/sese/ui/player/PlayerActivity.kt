package com.zrq.sese.ui.player

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.tabs.TabLayoutMediator
import com.zrq.sese.databinding.ActivityPlayerBinding
import com.zrq.sese.adapter.DetailAdapter
import com.zrq.sese.base.BaseVmActivity
import com.zrq.sese.db.MyDatabase
import com.zrq.sese.db.RoomController
import com.zrq.sese.db.table.HistoryTable
import com.zrq.sese.entity.VideoItem
import com.zrq.sese.view.MyBaseVideoView
import com.zrq.sese.view.MyBaseVideoView.STATE_PLAYING
import com.zrq.sese.view.MyPrepareView
import com.zrq.sese.view.MyStandardVideoController
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videocontroller.component.*

class PlayerActivity : BaseVmActivity<ActivityPlayerBinding, PlayerViewModel>() {

    private lateinit var detailAdapter: DetailAdapter

    private var video: VideoItem? = null
    private var isDestroy = false

    override fun providedViewBinding(): ActivityPlayerBinding {
        return ActivityPlayerBinding.inflate(layoutInflater)
    }

    override fun providedViewModel(): Class<PlayerViewModel> {
        return PlayerViewModel::class.java
    }

    override fun initViewModel(): Context {
        return this
    }

    override fun initData() {
        video = intent.getSerializableExtra("video") as VideoItem ?: VideoItem()
        viewModel.id = video?.id.toString()
        Log.d(TAG, "initData: ${viewModel.id}")
        val transition = intent.getStringExtra("transition") ?: ""
        detailAdapter = DetailAdapter(this, viewModel)

        binding.apply {
            Glide.with(this@PlayerActivity)
                .load(cover)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        videoView.setPlayerBackground(resource)
                        return false
                    }
                })
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        videoView.setPlayerBackground(resource)
                    }
                })
            videoView.transitionName = transition
            viewPager.offscreenPageLimit = 2
            viewPager.adapter = detailAdapter
            TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
                tab.text = if (position == 0) "相关推荐" else "评论"
            }.attach()
        }
        viewModel.loadVideo(path) {
            setVideoPath(it)
        }
    }

    override fun initEvent() {
        viewModel.commentSum.observe(this) {
            binding.tabLayout.getTabAt(1)?.text = "评论 $it"
        }
        binding.videoView.setOnStateChangeListener(object : MyBaseVideoView.OnStateChangeListener {
            override fun onPlayerStateChanged(playerState: Int) {

            }

            override fun onPlayStateChanged(playState: Int) {
                if (playState == STATE_PLAYING) {
                    //开始播放
                    RoomController.historyDao().insert(HistoryTable())
                }
            }
        })
    }


    private fun setVideoPath(hls: String) {
        if (isDestroy) {
            Log.d(TAG, "isDestroy: ")
            return
        }
        Handler(Looper.getMainLooper()).post {
            binding.apply {
                videoView.setUrl(hls)
                val controller = MyStandardVideoController(this@PlayerActivity)
                val completeView = CompleteView(this@PlayerActivity)
                val errorView = ErrorView(this@PlayerActivity)
                val prepareView = MyPrepareView(this@PlayerActivity)
                val titleView = TitleView(this@PlayerActivity)
                controller.addControlComponent(completeView, errorView, prepareView, titleView)
                controller.addControlComponent(VodControlView(this@PlayerActivity))
                controller.addControlComponent(GestureView(this@PlayerActivity))
                controller.setCanChangePosition(true)
                videoView.setVideoController(controller)
                videoView.start()
            }
        }
    }

    override fun onBackPressed() {
        val back = !binding.videoView.onBackPressed()
        if (back) {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        isDestroy = false
        binding.videoView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        isDestroy = true
        binding.videoView.release()
    }

    companion object {
        const val TAG = "PlayerActivity"
    }
}