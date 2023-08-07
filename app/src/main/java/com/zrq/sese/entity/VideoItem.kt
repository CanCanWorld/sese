package com.zrq.sese.entity

import androidx.annotation.Keep

@Keep
data class VideoItem(
    val id : String,
    val title: String,
    val path: String,
    val cover: String,
    val preview: String,
    val up: String,
    val duration: String,
    var isPlayer : Boolean = false
)