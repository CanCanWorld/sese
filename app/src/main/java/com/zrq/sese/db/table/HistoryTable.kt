package com.zrq.sese.db.table

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "vid")
    val vid: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "path")
    val path: String,

    @ColumnInfo(name = "cover")
    val cover: String,

    @ColumnInfo(name = "preview")
    val preview: String,

    @ColumnInfo(name = "up")
    val up: String,

    @ColumnInfo(name = "duration")
    val duration: String,

)