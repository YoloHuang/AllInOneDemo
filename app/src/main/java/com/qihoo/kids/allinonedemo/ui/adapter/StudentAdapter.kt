package com.qihoo.kids.allinonedemo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.qihoo.kids.allinonedemo.R
import com.qihoo.kids.allinonedemo.databinding.ItemStudentBinding
import com.qihoo.kids.allinonedemo.db.Student

/**
 * @author yolo.huang
 * @date 2020/12/28
 */
class StudentAdapter :BaseQuickAdapter<Student,BaseDataBindingHolder<ItemStudentBinding>>(R.layout.item_student) {
    override fun convert(holder: BaseDataBindingHolder<ItemStudentBinding>, item: Student) {
        holder.dataBinding?.student = item
    }
}