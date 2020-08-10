package com.qihoo.kids.allinonedemo.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.qihoo.kids.allinonedemo.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * @author yolo.huang
 * @date 2020/8/10
 */
class KtxViewModel(application: Application):BaseViewModel(application) {

    fun makeNetWorkRequest(){
        //ktx viewModel 可以使用viewModelScope直接启动一个携程
        viewModelScope.launch {

        }
    }
}