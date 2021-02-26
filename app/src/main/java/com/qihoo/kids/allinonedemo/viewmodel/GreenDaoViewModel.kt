package com.qihoo.kids.allinonedemo.viewmodel

import android.app.Application
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.qihoo.kids.allinonedemo.base.BaseViewModel
import com.qihoo.kids.allinonedemo.db.Student
import com.qihoo.kids.allinonedemo.db.StudentDaoManager

/**
 * @author yolo.huang
 * @date 2020/12/28
 */
class GreenDaoViewModel(application: Application) :BaseViewModel(application) {


    val resultString :MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val studentList:MutableLiveData<MutableList<Student>> by lazy {
        MutableLiveData<MutableList<Student>>()
    }

    val size = ObservableInt(0)

    var id = 0


    fun insertList(){
        val students = mutableListOf<Student>()
        for (i in 0..10){
            val age = (15..18).random()
            val student = Student(i.toLong(), "张华$i",age)
            students.add(student)
        }
        StudentDaoManager.insertStudentList(students) {
            if(it){
                resultString.value = "插入10条数据成功"
            }else{
                resultString.value = "插入10条数据失败"
            }
            queryAllList()

        }
    }

    private fun queryAllList(){
        StudentDaoManager.queryAllStudent {
            if(it!=null){
                studentList.value = it
                size.set(it.size)
            }else{
                studentList.value = null
                size.set(0)
            }
        }
    }

    fun deleteStudent(){
        val student = Student(5.toLong())
        StudentDaoManager.deleteStudent(student){
            if(it){
                resultString.value = "删除ID为5的数据成功"
            }else{
                resultString.value = "删除ID为5的数据失败"
            }
            queryAllList()
        }
    }

    fun updateStudent(){
        val student = Student(4.toLong(),"花花",19)
        StudentDaoManager.insertStudent(student){
            if(it){
                resultString.value = "更新ID为4的数据成功"
            }else{
                resultString.value = "更新ID为4的数据失败"
            }
            queryAllList()
        }
    }

    fun queryStudent(){
        StudentDaoManager.queryStudent(id.toLong()) {
            if (it != null) {
                resultString.value = "查询到student${it.toString()}"
            } else {
                resultString.value = "当前表中没有该ID的数据"
            }
            queryAllList()
        }
    }

    fun queryOnFocusChanged(charSequence: CharSequence){
        id = charSequence.toString().toInt()
    }

}