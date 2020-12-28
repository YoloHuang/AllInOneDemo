package com.qihoo.kids.allinonedemo.ui

import androidx.lifecycle.observe
import com.qihoo.kids.allinonedemo.R
import com.qihoo.kids.allinonedemo.base.BaseFragment
import com.qihoo.kids.allinonedemo.databinding.FragmentGreenDaoBinding
import com.qihoo.kids.allinonedemo.ui.adapter.StudentAdapter
import com.qihoo.kids.allinonedemo.viewmodel.GreenDaoViewModel

/**
 * @author yolo.huang
 * @date 2020/12/28
 */
class GreenDaoFragment:BaseFragment<GreenDaoViewModel,FragmentGreenDaoBinding>() {
    override val layoutId = R.layout.fragment_green_dao

    override fun initView() {
        mDataBinding.viewModel = mViewModel
        val adapter = StudentAdapter()
        mDataBinding.adapter = adapter


        mViewModel.studentList.observe(viewLifecycleOwner) {
            adapter.setNewInstance(it)
        }
    }
}