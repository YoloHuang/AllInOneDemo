package com.qihoo.kids.allinonedemo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.qihoo.kids.allinonedemo.base.BaseActivity
import com.qihoo.kids.allinonedemo.base.BaseViewModel
import com.qihoo.kids.allinonedemo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<BaseViewModel,ActivityMainBinding>() {

    /**
     * 添加新的fragment需要在FragmentFactory中添加一个新的fragment，还需要在array添加一个新的title
     */
    private var typeList:MutableList<Int> = mutableListOf()

    init {
        typeList.add(FragmentFactory.TYPE_KTX)
        typeList.add(FragmentFactory.TYPE_DATA_BINDING)
        typeList.add(FragmentFactory.TYPE_COROUTINES)
        typeList.add(FragmentFactory.TYPE_GREEN_DAO)
        typeList.add(FragmentFactory.TYPE_SERVICE)

    }

    override fun initView() {
        val titleList = resources.getStringArray(R.array.tab_title)
        mDataBinding.vpMain.adapter = object :FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return typeList.size
            }

            override fun createFragment(position: Int): Fragment {
                return FragmentFactory.getFragment(typeList[position])
            }
        }
        mDataBinding.vpMain.offscreenPageLimit = typeList.size
        TabLayoutMediator(mDataBinding.tabMain,mDataBinding.vpMain){ tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    override val layoutId: Int
        get() = R.layout.activity_main
}