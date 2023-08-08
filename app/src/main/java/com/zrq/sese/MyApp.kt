package com.zrq.sese

import android.app.Application
import com.google.android.material.color.DynamicColors

/**
 *@ClassName: MyApp
 *@Description: 主app
 *@Author: zhangruiqian
 *@CreateTime: 2023-08-08 11:30
 *@Version: 1.0
 **/
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

}