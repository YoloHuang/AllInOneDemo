package com.qihoo.kids.allinonedemo.ui

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.qihoo.kids.allinonedemo.R
import com.qihoo.kids.allinonedemo.base.BaseFragment
import com.qihoo.kids.allinonedemo.base.BaseViewModel
import com.qihoo.kids.allinonedemo.databinding.FragmentCoroutinesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * @author yolo.huang
 * @date 2020/8/28
 */
class CoroutinesFragment : BaseFragment<BaseViewModel, FragmentCoroutinesBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_coroutines

    override fun initView() {
        test()
    }

    fun test() {
        lifecycleScope.launch(Dispatchers.Main) {
            Log.d("hzj", "time+" + System.currentTimeMillis())
            val first = async { getNum(20) }
            val second = async { getNum(40) }
            Log.d("hzj", "time+" + System.currentTimeMillis())
            //mDataBinding.textView.text = (first.await()+second.await()).toString()
            (1..10).asFlow().collect {
                //mDataBinding.textView.text = (it).toString()

            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            val channel = produce<Int> {
                for (i in 1..10) {
                    delay(1000)
                    send(i * i)
                }
                close()
            }
            for (y in channel) {
                mDataBinding.textView.text = (y).toString()

            }

        }
        mDataBinding.button.setOnClickListener {

        }


    }

    fun createFlow(): Flow<Int> = flow {
        for (i in 1..10) {
            delay(1000)
            emit(i)
        }
    }

    suspend fun getNum(num: Int): Int {
        delay(5000)
        return num * num
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}