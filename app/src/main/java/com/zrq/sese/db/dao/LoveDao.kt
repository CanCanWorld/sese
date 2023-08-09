package com.zrq.sese.db.dao

import androidx.room.*
import com.zrq.sese.db.table.LoveTable

/**
 *@ClassName: VideoDao
 *@Description: video dao
 *@Author: zhangruiqian
 *@CreateTime: 2023-08-09 17:18
 *@Version: 1.0
 **/
@Dao
interface LoveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg item: LoveTable)

    @Query("Select * from HistoryTable")
    fun queryAll(): MutableList<LoveTable>

    @Update
    fun update(item: LoveTable)

    @Delete
    fun delete(item: LoveTable)

}