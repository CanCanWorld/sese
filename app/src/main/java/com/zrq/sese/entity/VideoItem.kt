package com.zrq.sese.entity

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class VideoItem(

    val id: String = "",

    val title: String = "未知",

    val path: String = "",

    val cover: String = "",

    val preview: String = "",

    val up: String = "未知",

    val duration: String = "",

    var isPlayer: Boolean = false
) : Serializable