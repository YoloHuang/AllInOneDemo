package com.qihoo.kids.allinonedemo

import androidx.fragment.app.Fragment
import com.qihoo.kids.allinonedemo.ui.DataBindingFragment
import com.qihoo.kids.allinonedemo.ui.KtxFragment

/**
 * @author yolo.huang
 * @date 2020/8/6
 */
object FragmentFactory {

    const val TYPE_KTX = 101
    const val TYPE_DATA_BINDING = 102

    fun getFragment(type:Int): Fragment {
        when(type){
            TYPE_KTX -> return KtxFragment()
            TYPE_DATA_BINDING -> return DataBindingFragment()
            else -> return KtxFragment()
        }

    }
}