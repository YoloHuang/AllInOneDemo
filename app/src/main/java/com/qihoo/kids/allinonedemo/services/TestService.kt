package com.qihoo.kids.allinonedemo.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*


/**
 * @author yolo.huang
 * @date 2021/2/18
 */
class TestService(): Service() {

    inner class MyBinder: Binder() {
        val service:TestService
        get() = this@TestService
    }

    private val generator: Random = Random()

    private val binder = MyBinder()


    override fun onCreate() {
        super.onCreate()
        Log.i("TestService","onCreate - Thread Id = ${Thread.currentThread().id}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("TestService","onStartCommand - Thread Id = ${Thread.currentThread().id}")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("TestService","onDestroy - Thread Id = ${Thread.currentThread().id}")

    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("TestService","onUnbind - Thread Id = ${Thread.currentThread().id}")

        return super.onUnbind(intent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i("TestService","onBind - Thread Id = ${Thread.currentThread().id}")
        return binder
    }

    fun getRandomNumber(): Int {
        return generator.nextInt()
    }
}
