package com.zrq.sese.ui.player

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.zrq.sese.adapter.RelateAdapter
import com.zrq.sese.databinding.FragmentRelatedBinding
import com.zrq.sese.base.BaseFragment
import com.zrq.sese.network.entity.RelateEntityItem
import java.util.*
import kotlin.random.Random

class RelatedFragment(
    private val viewModel: PlayerViewModel
) : BaseFragment<FragmentRelatedBinding>() {
    override fun providedViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRelatedBinding {
        return FragmentRelatedBinding.inflate(inflater, container, false)
    }

    private lateinit var adapter: RelateAdapter
    private val list = mutableListOf<RelateEntityItem>()

    @SuppressLint("NotifyDataSetChanged")
    override fun initData() {

        adapter = RelateAdapter(requireContext(), list, { binding, position ->
            val transition = "cover$position"
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), binding.videoView, transition).toBundle()
            startActivity(Intent(requireContext(), PlayerActivity::class.java).apply {
                putExtra("title", list[position].tf)
                putExtra("path", list[position].u)
                putExtra("cover", list[position].il)
                putExtra("id", list[position].id.toString())
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
        binding.apply {
            recyclerView.adapter = adapter
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initEvent() {
        viewModel.relates.observe(this) {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        private const val TAG = "RelatedFragment"
    }
}
