package com.qihoo.kids.allinonedemo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.qihoo.kids.allinonedemo.R
import com.qihoo.kids.allinonedemo.bean.TimezoneBean
import com.qihoo.kids.allinonedemo.databinding.ItemTimezoneBinding

/**
 * @author yolo.huang
 * @date 2021/3/1
 */
class TimezoneAdapter:BaseQuickAdapter<TimezoneBean, BaseDataBindingHolder<ItemTimezoneBinding>>(R.layout.item_timezone) {
    override fun convert(holder: BaseDataBindingHolder<ItemTimezoneBinding>, item: TimezoneBean) {
        holder.dataBinding?.timezone = item
    }
}