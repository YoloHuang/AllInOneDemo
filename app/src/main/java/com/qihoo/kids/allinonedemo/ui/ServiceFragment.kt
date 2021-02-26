package com.qihoo.kids.allinonedemo.ui

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.qihoo.kids.allinonedemo.R
import com.qihoo.kids.allinonedemo.base.BaseFragment
import com.qihoo.kids.allinonedemo.databinding.FragmentServiceBinding
import com.qihoo.kids.allinonedemo.services.TestService
import com.qihoo.kids.allinonedemo.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.fragment_service.*

/**
 * @author yolo.huang
 * @date 2021/1/27
 */
class ServiceFragment:BaseFragment<ServiceViewModel,FragmentServiceBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_service

    private  var service: TestService? = null
    private var isBind = false

    private val connect = object :ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            isBind = true
            val myBinder: TestService.MyBinder = binder as TestService.MyBinder
            service = myBinder.service
            Log.i("ServiceFragment", "ServiceFragment - onServiceConnected")
            val num: Int = service?.getRandomNumber()?:0
            Log.i("ServiceFragment", "ServiceFragment - getRandomNumber = $num")
        }

    }

    override fun initView() {
        bt_start_service.setOnClickListener {
            val intent = Intent(activity, TestService::class.java)
            activity?.startService(intent)
        }

        bt_stop_service.setOnClickListener {
            val intent = Intent(activity, TestService::class.java)
            activity?.stopService(intent)
        }

        bt_bind_service.setOnClickListener {
            val intent = Intent(activity, TestService::class.java)
            activity?.bindService(intent,connect,BIND_AUTO_CREATE)
        }

        bt_unbind_service.setOnClickListener {
            activity?.unbindService(connect)
        }
    }
}