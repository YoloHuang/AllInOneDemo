package com.qihoo.kids.allinonedemo.viewmodel

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.ObservableInt
import com.qihoo.kids.allinonedemo.BR
import com.qihoo.kids.allinonedemo.base.BaseViewModel
import com.qihoo.kids.allinonedemo.bindadapter.cleanStringToSecond
import com.qihoo.kids.allinonedemo.utils.DefaultTimer
import java.util.*
import kotlin.math.round

/**
 * @author yolo.huang
 * @date 2020/8/10
 */
class TimeViewModel(application:Application):BaseViewModel(application) {

    companion object{
        const val INITIAL_SECONDS_PER_WORK_SET = 50 // Seconds
        const val INITIAL_SECONDS_PER_REST_SET = 20 // Seconds
        const val INITIAL_NUMBER_OF_SETS = 5
    }
    var workTime = ObservableInt(INITIAL_SECONDS_PER_WORK_SET*10)
    var restTime = ObservableInt(INITIAL_SECONDS_PER_REST_SET*10)

    var workTimeLeft = ObservableInt(workTime.get())
    var resetTimeLeft  = ObservableInt(restTime.get())

    var numberTimes = INITIAL_NUMBER_OF_SETS
    var numberTimesElapsed = 0

    var numberSets:Array<Int> = emptyArray()
        @Bindable get() {
            return arrayOf(numberTimesElapsed,numberTimes)
        }
    set(value) {
        val newTotal = value[1]
        if(newTotal == numberSets[1]) return
        if (newTotal != 0 && newTotal > numberTimesElapsed) {
            field = value
            numberTimes = newTotal
        }
        notifyPropertyChanged(BR.numberSets)
    }

    var timeRunning:Boolean
        @Bindable get() {
            return state == TimeState.STARTED
        }
        set(value) {
            if (value) startButtonClicked() else pauseButtonClicked()
        }

    var stage = StartedStages.WORKING
    var state = TimeState.STOPPED

    val isWorkStages:Boolean
        @Bindable get() {
            return stage == StartedStages.WORKING
        }

    private fun pauseButtonClicked() {
        if(state==TimeState.STARTED){
            DefaultTimer.resetPauseTime()
            state = TimeState.PAUSED
            notifyPropertyChanged(BR.timeRunning)
        }
    }

    private fun startButtonClicked() {
        when(state){
            TimeState.STOPPED -> stopToStart()
            TimeState.PAUSED -> pauseToStart()
        }

        DefaultTimer.startTimer(object :TimerTask(){
            override fun run() {
                if(state == TimeState.STARTED){
                    updateCountsDown()
                }
            }
        })
    }

    private fun pauseToStart() {
        DefaultTimer.updateStartTime()
        state = TimeState.STARTED
        notifyPropertyChanged(BR.timeRunning)
    }

    private fun stopToStart() {
        DefaultTimer.resetStartTime()
        state = TimeState.STARTED
        stage = StartedStages.WORKING
        notifyPropertyChanged(BR.workStages)
        notifyPropertyChanged(BR.timeRunning)

    }

    fun updateCountsDown(){
        if(state == TimeState.STOPPED){
            resetTimer()
            return
        }

        val leftTime = if(state==TimeState.STARTED){
            DefaultTimer.getElapsedTime()
        }else{
            DefaultTimer.getPausedTime()
        }
        if(stage == StartedStages.WORKING){
            updateWorkTime(leftTime)
        }else{
            updateRestTime(leftTime)
        }
    }

    private fun updateRestTime(leftTime: Long) {
        stage = StartedStages.RESTING
        val newRestLeft = restTime.get() - (leftTime/100).toInt()
        resetTimeLeft.set(newRestLeft.coerceAtLeast(0))
        if(newRestLeft<=0){
            restFinished()
        }
    }

    private fun restFinished() {
        stage = StartedStages.WORKING
        numberTimesElapsed+=1
        resetTimer()
        if(numberTimesElapsed>=numberTimes){
            allFinish()
        }else{
            startWorkFromRest()
        }
        notifyPropertyChanged(BR.workStages)
        notifyPropertyChanged(BR.numberSets)

    }

    private fun startWorkFromRest() {
        stage = StartedStages.WORKING
        DefaultTimer.resetStartTime()
        resetTimeLeft.set(restTime.get())
        notifyPropertyChanged(BR.workStages)
    }

    fun allFinish() {
        DefaultTimer.resetTimer()
        state = TimeState.STOPPED
        stage = StartedStages.WORKING
        numberTimesElapsed= 0
        resetTimer()

        notifyPropertyChanged(BR.workStages)
        notifyPropertyChanged(BR.timeRunning)
        notifyPropertyChanged(BR.numberSets)

    }

    private fun updateWorkTime(leftTime: Long) {
        stage = StartedStages.WORKING
        val newWorkLeft = workTime.get() - (leftTime/100).toInt()
        if(newWorkLeft<=0){
            workFinished()
        }
        workTimeLeft.set(newWorkLeft.coerceAtLeast(0))
    }

    private fun workFinished() {
        stage = StartedStages.RESTING
        DefaultTimer.resetStartTime()
        notifyPropertyChanged(BR.workStages)
    }

    private fun resetTimer(){
        workTimeLeft.set(workTime.get())
        resetTimeLeft.set(restTime.get())
    }

    fun resetButtonClicked(){
        allFinish()
    }

    fun decreaseNumberButtonClicked(){
        if(numberTimes>=numberTimesElapsed){
            numberTimes-=1
            notifyPropertyChanged(BR.numberSets)
        }
    }
    fun increaseNumberButtonClicked(){
        numberTimes+=1
        notifyPropertyChanged(BR.numberSets)

    }

    fun decreaseWorkButtonClicked() = timeChange(workTime,-1,10)
    fun increaseWorkButtonClicked() = timeChange(workTime,1)

    fun decreaseRestButtonClicked() = timeChange(restTime,-1)
    fun increaseRestButtonClicked() = timeChange(restTime,1)

    fun workOnFocusChanged(charSequence: CharSequence){
        try{
            val value = cleanStringToSecond(charSequence.toString())
            //这里这么操作一番，是为了让workTime有变化，直接使用coerceAtLeast的话，
            //如果之前workTime就为10，那么workTime就没变，不会更新UI,所以先设为0，再更新为10 ，UI就会变化
            if(value ==0){
                workTime.set(value)
            }
            workTime.set(value.coerceAtLeast(10))
        } catch (e:NumberFormatException){
            return
        }
        if(!timeRunning){
            workTimeLeft.set(workTime.get())
        }
    }
    fun restOnFocusChanged(charSequence: CharSequence){
        try {
            restTime.set(cleanStringToSecond(charSequence.toString()))
        } catch (e: NumberFormatException) {
            return
        }
        if(stage == StartedStages.WORKING){
            resetTimeLeft.set(restTime.get())
        }
    }

    private fun timeChange(time: ObservableInt, sign: Int = 1, min: Int = 0) {
        if(time.get()<10 && sign<0) return
        roundTimeIncrease(time,sign,min)
        if(state == TimeState.STOPPED){
            workTimeLeft.set(workTime.get())
            resetTimeLeft.set(restTime.get())
        }else{
            if(stage==StartedStages.WORKING){
                resetTimeLeft.set(restTime.get())
            }
            updateCountsDown()
        }
    }

    private fun roundTimeIncrease(time: ObservableInt, sign: Int, min: Int) {
        val currentTime = time.get()
        val newTime = when{
            currentTime<100 -> currentTime+sign*10
            currentTime<600 -> (round(currentTime/50.0)*50+sign*50).toInt()
            else -> (round(currentTime/100.0)*100+sign*100).toInt()
        }
        time.set(newTime.coerceAtLeast(min))
    }
}

private operator fun ObservableInt.plusAssign(value: Int) {
    set(get() + value)
}

private operator fun ObservableInt.minusAssign(amount: Int) {
    plusAssign(- amount)
}

enum class TimeState{STARTED,PAUSED,STOPPED}
enum class StartedStages{WORKING, RESTING}
