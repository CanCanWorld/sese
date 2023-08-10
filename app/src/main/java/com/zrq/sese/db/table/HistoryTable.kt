package com.zrq.sese.db.table

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.zrq.sese.entity.VideoItem
import java.io.Serializable

/**
 *@ClassName: VideoTable
 *@Description: Video表字段映射
 *@Author: zhangruiqian
 *@CreateTime: 2023-08-09 17:08
 *@Version: 1.0
 **/
@Keep
@Entity
data class HistoryTable(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "path")
    var path: String = "",

    @ColumnInfo(name = "cover")
    var cover: String = "",

    @ColumnInfo(name = "preview")
    var preview: String = "",

    @ColumnInfo(name = "up")
    var up: String = "",

    @ColumnInfo(name = "duration")
    var duration: String = "",

    @Ignore
    var isPlayer: Boolean = false,

    ) {
    constructor() : this("", "", "", "", "", "", "", false)

    fun toVideoItem(): VideoItem {
        return VideoItem(id, title, path, cover, preview, up, duration, isPlayer)
    }
}