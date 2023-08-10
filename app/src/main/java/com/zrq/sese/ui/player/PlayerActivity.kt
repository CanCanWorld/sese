package com.zrq.sese.ui.player

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.tabs.TabLayoutMediator
import com.zrq.sese.R
import com.zrq.sese.databinding.ActivityPlayerBinding
import com.zrq.sese.adapter.DetailAdapter
import com.zrq.sese.base.BaseVmActivity
import com.zrq.sese.db.RoomController
import com.zrq.sese.db.table.HistoryTable
import com.zrq.sese.entity.VideoItem
import com.zrq.sese.view.MyBaseVideoView
import com.zrq.sese.view.MyBaseVideoView.STATE_PLAYING
import com.zrq.sese.view.MyPrepareView
import com.zrq.sese.view.MyStandardVideoController
import xyz.doikki.videocontroller.component.*

class PlayerActivity : BaseVmActivity<ActivityPlayerBinding, PlayerViewModel>() {

    private lateinit var detailAdapter: DetailAdapter

    private var video: VideoItem = VideoItem()
    private var isDestroy = false
    private var isLove = false

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
        video = intent.getSerializableExtra("video") as VideoItem
        viewModel.id = video.id
        Log.d(TAG, "initData: ${viewModel.id}")
        val transition = intent.getStringExtra("transition") ?: ""
        detailAdapter = DetailAdapter(this, viewModel)

        binding.apply {
            Glide.with(this@PlayerActivity)
                .load(video.cover)
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
        viewModel.loadVideo(video.path) {
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
                    RoomController.historyDao().insert(video.toHistory())
                }
            }
        })
        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_back -> {
                    finish()
                }
                R.id.item_download -> {
                    Toast.makeText(this, "暂未开发", Toast.LENGTH_SHORT).show()
                }
                R.id.item_full -> {
                    binding.videoView.startFullScreen()
                }
                else -> {}
            }
            false
        }
        binding.fabLove.setOnClickListener {
            isLove = if (isLove) {
                RoomController.loveDao().delete(video.toLove())
                binding.fabLove.setBackgroundResource(R.drawable.ic_weiguanzhu)
                false
            } else {
                RoomController.loveDao().insert(video.toLove())
                binding.fabLove.setBackgroundResource(R.drawable.ic_yiguanzhu)
                true
            }
        }
    }


    private fun setVideoPath(path: String) {
        if (isDestroy) {
            Log.d(TAG, "isDestroy: ")
            return
        }
        Handler(Looper.getMainLooper()).post {
            binding.apply {
                videoView.setUrl(path)
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