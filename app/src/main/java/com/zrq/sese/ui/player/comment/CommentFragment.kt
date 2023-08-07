package com.zrq.sese.ui.player.comment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zrq.sese.adapter.CommentAdapter
import com.zrq.sese.base.BaseFragment
import com.zrq.sese.databinding.FragmentCommentBinding
import com.zrq.sese.entity.CommentItem
import com.zrq.sese.ui.player.PlayerViewModel

class CommentFragment(
    private val viewModel: PlayerViewModel
) : BaseFragment<FragmentCommentBinding>() {
    override fun providedViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCommentBinding {
        return FragmentCommentBinding.inflate(inflater, container, false)
    }

    private lateinit var adapter: CommentAdapter

    private val list = mutableListOf<CommentItem>()


    override fun initData() {
        viewModel.loadComment()
        adapter = CommentAdapter(requireContext(), list) {}
        binding.apply {
            recyclerView.adapter = adapter
        }
    }

    override fun initEvent() {
        viewModel.apply {
            comments.observe(this@CommentFragment) {
                Log.d(TAG, "it: $it")
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object{
        private const val TAG = "CommentFragment"
    }

}
