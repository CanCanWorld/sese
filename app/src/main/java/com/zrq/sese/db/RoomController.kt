package com.zrq.sese.db

import android.content.Context
import androidx.room.Room
import java.lang.ref.WeakReference

/**
 *@ClassName: RoomController
 *@Description: Room 数据库管理工具
 *@Author: zhangruiqian
 *@CreateTime: 2023-08-09 17:23
 *@Version: 1.0
 **/
object RoomController {

    private var context: WeakReference<Context>? = null
    private lateinit var database: MyDatabase

    fun initRoom(ctx: Context): MyDatabase {
        context = WeakReference(ctx)
        database = Room.databaseBuilder(ctx, MyDatabase::class.java, "db_sese")
            .allowMainThreadQueries()
            .build()
        return database
    }

    fun historyDao() = database.historyDao()
    fun loveDao() = database.loveDao()

}