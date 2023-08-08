package com.zrq.sese.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zrq.sese.ui.player.PlayerViewModel
import com.zrq.sese.ui.player.comment.CommentFragment
import com.zrq.sese.ui.player.related.RelatedFragment

class DetailAdapter(
    fragmentActivity: FragmentActivity,
    private val viewModel: PlayerViewModel
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            RelatedFragment(viewModel)
        } else {
            CommentFragment(viewModel)
        }
    }
}
