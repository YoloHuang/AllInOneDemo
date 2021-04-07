package com.qihoo.kids.allinonedemo.viewmodel

import android.app.Application
import android.icu.util.TimeZone
import androidx.lifecycle.MutableLiveData
import com.qihoo.kids.allinonedemo.base.BaseViewModel
import com.qihoo.kids.allinonedemo.bean.TimezoneBean
import java.util.*

/**
 * @author yolo.huang
 * @date 2021/3/1
 */
class TimezoneViewModel(application:Application):BaseViewModel(application) {

    val timezoneList: MutableLiveData<MutableList<TimezoneBean>> by lazy {
        MutableLiveData<MutableList<TimezoneBean>>()
    }

    fun getTimezone(){
        val appCountry = Locale.getDefault().country
        val timezoneIds = TimeZone.getAvailableIDs(appCountry)
        val timezoneNames = mutableListOf<String>()
        var timezonelist2 = mutableListOf<TimezoneBean>()

        for (timezoneId in timezoneIds){
            val timezoneName = TimeZone.getTimeZone(timezoneId).displayName
            if(!timezoneNames.contains(timezoneName)){
                timezoneNames.add(timezoneName)
                val timezone = TimezoneBean(timezoneId,timezoneName,TimeZone.getTimeZone(timezoneId).getDisplayName(false,TimeZone.LONG_GMT))
                timezonelist2.add(timezone)
            }
        }

        timezoneList.value = timezonelist2
    }
}