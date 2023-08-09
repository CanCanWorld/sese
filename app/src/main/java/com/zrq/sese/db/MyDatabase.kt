package com.zrq.sese.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zrq.sese.db.table.HistoryTable
import com.zrq.sese.db.dao.HistoryDao
import com.zrq.sese.db.dao.LoveDao
import com.zrq.sese.db.table.LoveTable

/**
 *@ClassName: MyDatabase
 *@Description: MyDatabase
 *@Author: zhangruiqian
 *@CreateTime: 2023-08-09 17:25
 *@Version: 1.0
 **/
@Database(
    entities = [HistoryTable::class, LoveTable::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun loveDao(): LoveDao
}