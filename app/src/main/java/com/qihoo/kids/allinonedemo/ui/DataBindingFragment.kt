package com.qihoo.kids.allinonedemo.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import com.qihoo.kids.allinonedemo.BR
import com.qihoo.kids.allinonedemo.R
import com.qihoo.kids.allinonedemo.base.BaseFragment
import com.qihoo.kids.allinonedemo.databinding.FragmentDataBindingBinding
import com.qihoo.kids.allinonedemo.viewmodel.TimeViewModel
import java.util.*

/**
 * @author yolo.huang
 * @date 2020/8/10
 */
class DataBindingFragment:BaseFragment<TimeViewModel,FragmentDataBindingBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_data_binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState == null){
            restorePreferences(mViewModel)
        }
    }


    override fun initView() {
        mDataBinding.viewModel = mViewModel


        addChangedCallBack(mViewModel.restTime,SHARED_PREFS_TIME_WORK)
        addChangedCallBack(mViewModel.workTime, SHARED_PREFS_TIME_REST)
        addNumberChangedCallBack(mViewModel)
    }

    private fun addChangedCallBack(time: ObservableInt, key: String) {
        time.addOnPropertyChangedCallback(object :Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val sharedPreferences = mActivity.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)?:return
                sharedPreferences.edit {
                    putInt(key,time.get())
                }
            }
        })
    }

    private fun addNumberChangedCallBack(viewModel: TimeViewModel){
        viewModel.addOnPropertyChangedCallback(object :Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(propertyId == BR.numberSets){
                    val sharedPreferences = mActivity.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)?:return
                    sharedPreferences.edit {
                        putInt(SHARED_PREFS_SET,viewModel.numberSets[1])
                    }
                }
            }
        })
    }

    private fun restorePreferences(viewModel: TimeViewModel) {
        val sharedPreferences = mActivity.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)?:return
        if(sharedPreferences.contains(SHARED_PREFS_TIME_WORK)){
            viewModel.workTime.set(sharedPreferences.getInt(SHARED_PREFS_TIME_WORK,100))
        }
        if(sharedPreferences.contains(SHARED_PREFS_TIME_REST)){
            viewModel.restTime.set(sharedPreferences.getInt(SHARED_PREFS_TIME_REST,50))
        }
        if(sharedPreferences.contains(SHARED_PREFS_SET)){
            viewModel.numberSets = arrayOf(0,sharedPreferences.getInt(SHARED_PREFS_SET,5))
        }
        viewModel.allFinish()
    }

    companion object {
        const val SHARED_PREFS_KEY = "timer"
        const val SHARED_PREFS_TIME_WORK = "SHARED_PREFS_TIME_WORK"
        const val SHARED_PREFS_TIME_REST = "SHARED_PREFS_TIME_REST"
        const val SHARED_PREFS_SET = "SHARED_PREFS_SET"

    }
}