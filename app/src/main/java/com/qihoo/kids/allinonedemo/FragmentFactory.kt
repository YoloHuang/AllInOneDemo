package com.qihoo.kids.allinonedemo

import androidx.fragment.app.Fragment
import com.qihoo.kids.allinonedemo.ui.CoroutinesFragment
import com.qihoo.kids.allinonedemo.ui.DataBindingFragment
import com.qihoo.kids.allinonedemo.ui.GreenDaoFragment
import com.qihoo.kids.allinonedemo.ui.KtxFragment

/**
 * @author yolo.huang
 * @date 2020/8/6
 */
object FragmentFactory {

    const val TYPE_KTX = 101
    const val TYPE_DATA_BINDING = 102
    const val TYPE_COROUTINES = 103
    const val TYPE_GREEN_DAO = 104


    fun getFragment(type:Int): Fragment {
        return when(type){
            TYPE_KTX -> KtxFragment()
            TYPE_DATA_BINDING -> DataBindingFragment()
            TYPE_COROUTINES -> CoroutinesFragment()
            TYPE_GREEN_DAO -> GreenDaoFragment()
            else -> KtxFragment()
        }

    }
}