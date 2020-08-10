package com.qihoo.kids.allinonedemo.ui

import com.qihoo.kids.allinonedemo.R
import com.qihoo.kids.allinonedemo.base.BaseFragment
import com.qihoo.kids.allinonedemo.base.BaseViewModel
import com.qihoo.kids.allinonedemo.databinding.FragmentDataBindingBinding

/**
 * @author yolo.huang
 * @date 2020/8/10
 */
class DataBindingFragment:BaseFragment<BaseViewModel,FragmentDataBindingBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_data_binding

    override fun initView() {

    }
}