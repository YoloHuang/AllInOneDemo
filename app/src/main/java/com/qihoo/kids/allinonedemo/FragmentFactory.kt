package com.qihoo.kids.allinonedemo

import androidx.fragment.app.Fragment
import com.qihoo.kids.allinonedemo.ui.*

/**
 * @author yolo.huang
 * @date 2020/8/6
 */
object FragmentFactory {

    const val TYPE_KTX = 101
    const val TYPE_DATA_BINDING = 102
    const val TYPE_COROUTINES = 103
    const val TYPE_GREEN_DAO = 104
    const val TYPE_SERVICE = 105
    const val TYPE_TIMEZONE = 106


    fun getFragment(type:Int): Fragment {
        return when(type){
            TYPE_KTX -> KtxFragment()
            TYPE_DATA_BINDING -> DataBindingFragment()
            TYPE_COROUTINES -> CoroutinesFragment()
            TYPE_GREEN_DAO -> GreenDaoFragment()
            TYPE_SERVICE -> ServiceFragment()
            TYPE_TIMEZONE -> TimezoneFragment()
            else -> KtxFragment()
        }

    }
}