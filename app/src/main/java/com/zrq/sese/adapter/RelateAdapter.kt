package com.zrq.sese.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.zrq.sese.databinding.ItemRelateBinding
import com.zrq.sese.network.entity.RelateEntityItem
import com.zrq.sese.utils.Covert.picToVideo

class RelateAdapter(
    private val context: Context,
    private val list: MutableList<RelateEntityItem>,
    private val onItemClick: (ItemRelateBinding, Int) -> Unit,
    private val onItemLongClick: (Int) -> Unit,
) : RecyclerView.Adapter<VH<ItemRelateBinding>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<ItemRelateBinding> {
        val inflate = ItemRelateBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(inflate)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH<ItemRelateBinding>, position: Int) {
        val item = list[position]
        holder.binding.apply {
            videoView.setPlayerBackgroundColor(Color.BLACK)
            Glide.with(context)
                .load(item.il)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        videoView.setPlayerBackground(resource)
                    }
                })

            if (item.isPlayer) {
                videoView.release()
                videoView.setUrl(picToVideo(item.i))
                videoView.setLooping(true)
                videoView.start()
            } else {
                videoView.release()
            }
            tvName.text = item.pn
            tvTitle.text = item.tf
            tvDuration.text = item.d
            root.setOnClickListener { onItemClick(this, position) }
            root.setOnLongClickListener {
                onItemLongClick(position)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}