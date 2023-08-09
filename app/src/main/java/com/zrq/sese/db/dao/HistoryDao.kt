package com.zrq.sese.db.dao

import androidx.room.*
import com.zrq.sese.db.table.HistoryTable

/**
 *@ClassName: VideoDao
 *@Description: video dao
 *@Author: zhangruiqian
 *@CreateTime: 2023-08-09 17:18
 *@Version: 1.0
 **/
@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg item: HistoryTable)

    @Query("Select * from HistoryTable")
    fun queryAll(): MutableList<HistoryTable>

    @Update
    fun update(item: HistoryTable)

    @Delete
    fun delete(item: HistoryTable)

}