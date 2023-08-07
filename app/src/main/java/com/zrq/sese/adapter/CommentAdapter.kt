package com.zrq.sese.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zrq.sese.databinding.ItemCommentBinding
import com.zrq.sese.entity.CommentItem

class CommentAdapter(
    private val context: Context,
    private val list: MutableList<CommentItem>,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<VH<ItemCommentBinding>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<ItemCommentBinding> {
        val inflate = ItemCommentBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(inflate)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH<ItemCommentBinding>, position: Int) {
        val item = list[position]
        holder.binding.apply {
            Glide.with(context)
                .load(item.pic)
                .into(ivFace)

            tvName.text = item.name
            tvDesc.text = "${item.time}   IP属地：${item.country}"
            tvMessage.text = item.message
            root.setOnClickListener { onItemClick(position) }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}