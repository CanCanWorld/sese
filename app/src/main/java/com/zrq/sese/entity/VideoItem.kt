package com.zrq.sese.entity

import androidx.annotation.Keep
import com.zrq.sese.db.table.HistoryTable
import com.zrq.sese.db.table.LoveTable
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
) : Serializable {
    fun toHistory(): HistoryTable {
        return HistoryTable(id, title, path, cover, preview, up, duration, isPlayer)
    }

    fun toLove(): LoveTable {
        return LoveTable(id, title, path, cover, preview, up, duration, isPlayer)
    }
}