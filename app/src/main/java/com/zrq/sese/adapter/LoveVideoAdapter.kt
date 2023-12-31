package com.zrq.sese.adapter

import android.content.Context
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
import com.zrq.sese.databinding.ItemHomeVideoBinding
import com.zrq.sese.db.table.LoveTable

class LoveVideoAdapter(
    private val context: Context,
    private val list: MutableList<LoveTable>,
    private val onItemClick: (ItemHomeVideoBinding, Int) -> Unit,
    private val onItemLongClick: (Int) -> Unit,
) : RecyclerView.Adapter<VH<ItemHomeVideoBinding>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<ItemHomeVideoBinding> {
        val inflate = ItemHomeVideoBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(inflate)
    }

    override fun onBindViewHolder(holder: VH<ItemHomeVideoBinding>, position: Int) {
        val item = list[position]
        holder.binding.apply {
            tvTitle.text = item.title
            tvUpName.text = item.up
            tvDuration.text = item.duration
            Glide.with(context)
                .load(item.cover)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        videoView.setPlayerBackground(resource)
                    }
                })

            if (item.isPlayer) {
                videoView.release()
                videoView.setUrl(item.preview)
                videoView.setLooping(true)
                videoView.start()
            } else {
                videoView.release()
            }

            root.setOnClickListener {
                onItemClick(this, position)
            }
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