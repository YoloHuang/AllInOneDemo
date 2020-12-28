package com.qihoo.kids.allinonedemo

import android.app.Application
import com.qihoo.kids.allinonedemo.db.GreenDaoManager

/**
 * @author yolo.huang
 * @date 2020/12/21
 */
class MyApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        GreenDaoManager.initDb(this)
    }

}