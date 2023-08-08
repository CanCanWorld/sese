package com.zrq.sese.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import com.zrq.sese.databinding.FragmentHomeBinding
import com.zrq.sese.adapter.HomeVideoAdapter
import com.zrq.sese.base.BaseVmFragment
import com.zrq.sese.entity.VideoItem
import com.zrq.sese.ui.player.PlayerActivity

class HomeFragment : BaseVmFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun providedViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun initViewModel(): Context {
        return requireContext()
    }

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
            refreshLayout.autoRefresh()
        }
        viewModel.list.observe(this) {
            list.addAll(it)
            mAdapter.notifyDataSetChanged()
            binding.refreshLayout.finishRefresh()
            binding.refreshLayout.finishLoadMore()
        }
        Log.d(TAG, "initEvent: ")
    }

    private fun load() {
        Log.d(TAG, "load: ")
        if (page == 1) {
            list.clear()
            mAdapter.notifyDataSetChanged()
        }
        url = if (keyword != "") {
            "https://xvideos.com/?k=$keyword&p=$page"
        } else {
            "https://xvideos.com/new/$page"
        }
        viewModel.load(url)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

}