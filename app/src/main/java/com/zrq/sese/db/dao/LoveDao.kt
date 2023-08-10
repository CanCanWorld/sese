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

    @Query("Select * from LoveTable")
    fun queryAll(): MutableList<LoveTable>

    @Update
    fun update(item: LoveTable): Int

    @Delete
    fun delete(item: LoveTable): Int

    @Query("Select * from LoveTable where id = :id")
    fun container(id: String): Boolean
}