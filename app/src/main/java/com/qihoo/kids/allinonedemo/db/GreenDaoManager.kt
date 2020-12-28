package com.qihoo.kids.allinonedemo.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.greenrobot.greendao.async.AsyncSession

/**
 * @author yolo.huang
 * @date 2020/12/21
 */
object GreenDaoManager {

    lateinit var daoSession: DaoSession

    private var mHelper: DaoMaster.OpenHelper? = null

    private var mDb: SQLiteDatabase? = null

    @Synchronized
    fun initDb(context:Context){
        if(mHelper ==null || mDb == null){
            mHelper = DaoMaster.DevOpenHelper(context,"testdao.db")
            mDb = mHelper?.writableDatabase
            daoSession = DaoMaster(mDb).newSession()
        }
    }

    @Synchronized
    fun getAsyncDaoSession():AsyncSession{
        return daoSession.startAsyncSession()
    }



}