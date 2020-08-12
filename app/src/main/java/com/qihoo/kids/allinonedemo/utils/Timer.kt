package com.qihoo.kids.allinonedemo.utils

import java.util.*

/**
 * @author yolo.huang
 * @date 2020/8/10
 */
interface Timer{

    fun resetTimer()

    fun startTimer(task: TimerTask)

    fun getPausedTime():Long
    fun getElapsedTime():Long
    fun resetPauseTime()
    fun updateStartTime()
    fun resetStartTime()

}

object DefaultTimer:Timer{

    const val TIMER_PERIOD_MS = 100L
    var startTime = System.currentTimeMillis()
    var pauseTime = 0L
    var timer = java.util.Timer()

    override fun resetTimer() {
        timer.cancel()
    }

    override fun startTimer(task: TimerTask) {
        timer= Timer()
        timer.scheduleAtFixedRate(task,0, TIMER_PERIOD_MS)
    }

    override fun getPausedTime(): Long {
        return pauseTime- startTime
    }

    override fun getElapsedTime(): Long {
        return System.currentTimeMillis()- startTime
    }

    override fun resetPauseTime() {
        pauseTime = System.currentTimeMillis()
    }


    override fun updateStartTime() {
        startTime +=System.currentTimeMillis() - pauseTime
    }

    override fun resetStartTime() {
        startTime = System.currentTimeMillis()
    }

}