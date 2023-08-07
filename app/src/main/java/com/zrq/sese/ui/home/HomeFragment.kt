package com.zrq.sese.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import com.zrq.sese.databinding.FragmentHomeBinding
import com.zrq.sese.adapter.HomeVideoAdapter
import com.zrq.sese.base.BaseFragment
import com.zrq.sese.entity.VideoItem
import com.zrq.sese.ui.player.PlayerActivity
import org.jsoup.Jsoup

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun providedViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, container, false)
    }

    private lateinit var mAdapter: HomeVideoAdapter
    private val list = mutableListOf<VideoItem>()
    private var page = 1
    private var url = ""
    private var keyword = ""

    override fun initData() {
        mAdapter = HomeVideoAdapter(requireContext(), list, { binding, position ->
            val transition = "cover$position"
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), binding.videoView, transition).toBundle()
            startActivity(Intent(requireContext(), PlayerActivity::class.java).apply {
                putExtra("title", list[position].title)
                putExtra("path", list[position].path)
                putExtra("cover", list[position].cover)
                putExtra("id", list[position].id)
                binding.videoView.transitionName = transition
                putExtra("transition", transition)
            }, options)
        }, {
            list.forEach { item ->
                item.isPlayer = false
            }
            list[it].isPlayer = true
            mAdapter.notifyDataSetChanged()
        })
        binding.apply {
            recyclerView.adapter = mAdapter
            refreshLayout.autoRefresh()
        }
    }

    override fun initEvent() {
        binding.apply {
            refreshLayout.setOnRefreshListener {
                page = 1
                load()
            }
            refreshLayout.setOnLoadMoreListener {
                page++
                load()
            }
            tvSearch.setOnClickListener {
                keyword = etSearch.text.toString()
                if ("" == keyword) {
                    Toast.makeText(requireContext(), "请输入关键字", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                page = 1
                load()
            }
            etSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    tvSearch.callOnClick()
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(requireActivity().window.decorView.windowToken, 0)
                }
                false
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun load() {
        if (page == 1) {
            list.clear()
            mAdapter.notifyDataSetChanged()
        }
        Thread {
            url = if (keyword != "") {
                "https://xvideos.com/?k=$keyword&p=$page"
            } else {
                "https://xvideos.com/new/$page"
            }
            val doc = Jsoup.connect(url).get()
            val elements = doc.getElementsByClass("thumb-block")
            elements.forEach {
                val id = it.attr("data-id")
                val thumbUnder = it.getElementsByClass("thumb-under")[0]
                val titleTag = thumbUnder.getElementsByClass("title")[0].getElementsByClass("title")[0].getElementsByTag("a")[0]
                val name = thumbUnder.getElementsByClass("name").text()
                val title = titleTag.attr("title")
                val path = titleTag.attr("href")
                val cover = it.getElementsByClass("thumb-inside")[0].getElementsByClass("thumb")[0].getElementsByTag("a")[0].getElementsByTag("img")[0].attr("data-src")
                val preview = picToVideo(cover)
                val duration = thumbUnder.getElementsByClass("duration")[0].text()
                list.add(VideoItem(id ,title, path, cover, preview, name, duration))
                Log.d(TAG, "id: $id")
                Log.d(TAG, "title: $title")
                Log.d(TAG, "path: $path")
                Log.d(TAG, "cover: $cover")
                Log.d(TAG, "name: $name")
                Log.d(TAG, "preview: $preview")
                Log.d(TAG, "duration: $duration")
            }
            Handler(Looper.getMainLooper()).post {
                mAdapter.notifyDataSetChanged()
                binding.refreshLayout.finishRefresh()
                binding.refreshLayout.finishLoadMore()
            }
        }.start()
    }


    //https://img-cf.xvideos-cdn.com/videos/videopreview/06/69/eb/0669eb02197aaf29a77b51eb72d252bb_169-2.mp4
    //https://img-cf.xvideos-cdn.com/videos/videopreview/06/69/eb/0669eb02197aaf29a77b51eb72d252bb-2.mp4
    //https://img-cf.xvideos-cdn.com/videos/thumbs169/06/69/eb/0669eb02197aaf29a77b51eb72d252bb-2/0669eb02197aaf29a77b51eb72d252bb.17.jpg

    //https://img-egc.xvideos-cdn.com/videos/thumbs169ll/63/12/d8/6312d87848556b57f308db846aacb6fe/6312d87848556b57f308db846aacb6fe.7.jpg
    //https://img-egc.xvideos-cdn.com/videos/videopreview/63/12/d8/6312d87848556b57f308db846aacb6fe_169-2.mp4

    //https://img-egc.xvideos-cdn.com/videos/thumbs169ll/e0/fb/38/e0fb387effe0e47b62a92605375a0fc1/e0fb387effe0e47b62a92605375a0fc1.5.jpg
    //https://img-egc.xvideos-cdn.com/videos/videopreview/e0/fb/38/e0fb387effe0e47b62a92605375a0fc1_169.mp4
    private fun picToVideo(cover: String): String {
        val index = cover.lastIndexOf('/')
        var target = cover.removeRange(index, cover.length)
        val indexOf = target.indexOf("/thumbs")
        val first = target.subSequence(0, indexOf)
        val sequence = target.subSequence(indexOf + 1, target.length)
        val last = sequence.subSequence(sequence.indexOf('/'), sequence.length)
        target = "${first}/videopreview${last}_169.mp4"
        return target
    }


    companion object {
        private const val TAG = "HomeFragment"
    }

}