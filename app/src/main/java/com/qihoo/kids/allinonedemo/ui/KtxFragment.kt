package com.qihoo.kids.allinonedemo.ui

import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.qihoo.kids.allinonedemo.R
import com.qihoo.kids.allinonedemo.base.BaseFragment
import com.qihoo.kids.allinonedemo.base.BaseViewModel
import com.qihoo.kids.allinonedemo.databinding.FragmentKtxBinding
import com.qihoo.kids.allinonedemo.viewmodel.KtxViewModel
import kotlinx.android.synthetic.main.fragment_ktx.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author yolo.huang
 * @date 2020/8/6
 */
class KtxFragment:BaseFragment<KtxViewModel,FragmentKtxBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_ktx

    override fun initView() {
        //fragment ktx 可以使用activityViewModels属性委托在一行中绑定viewmodel
        val viewmodel by activityViewModels<BaseViewModel>()
        //lifecycle ktx 可以使用lifecycleScope.launch 来使用协程
        viewLifecycleOwner.lifecycleScope.launch {
            val params = TextViewCompat.getTextMetricsParams(tv_ktx)
            val precomputedText = withContext(Dispatchers.Default){
                PrecomputedTextCompat.create("start",params)
            }
            TextViewCompat.setPrecomputedText(tv_ktx,precomputedText)
        }
        val user:LiveData<User> = liveData {
            val data = loadUser()
            emit(data)
        }

    }


    suspend fun loadUser(): User {
        delay(1000)
        return User("yolo", 25)
    }

    data class User(val name:String,val age:Int)
}