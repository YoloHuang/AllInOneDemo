package com.qihoo.kids.allinonedemo.ui

import androidx.lifecycle.observe
import com.qihoo.kids.allinonedemo.R
import com.qihoo.kids.allinonedemo.base.BaseFragment
import com.qihoo.kids.allinonedemo.databinding.FragmentTimezoneBinding
import com.qihoo.kids.allinonedemo.ui.adapter.TimezoneAdapter
import com.qihoo.kids.allinonedemo.viewmodel.TimezoneViewModel

/**
 * @author yolo.huang
 * @date 2021/2/26
 */
class TimezoneFragment: BaseFragment<TimezoneViewModel,FragmentTimezoneBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_timezone

    lateinit var adapter:TimezoneAdapter

    override fun initView() {

        adapter = TimezoneAdapter()
        mDataBinding.adapter = adapter

        mViewModel.timezoneList.observe(viewLifecycleOwner){
            adapter.setNewInstance(it)
        }

        mDataBinding.btTimezone.setOnClickListener {
            mViewModel.getTimezone()
        }

    }
}