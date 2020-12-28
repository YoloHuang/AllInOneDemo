package com.qihoo.kids.allinonedemo.db

import org.greenrobot.greendao.async.AsyncOperationListener

/**
 * @author yolo.huang
 * @date 2020/12/28
 */
object StudentDaoManager {

    fun insertStudent(student: Student,callback:(boolean:Boolean)->Unit){
        val mAsyncSession = GreenDaoManager.getAsyncDaoSession()
        mAsyncSession.listenerMainThread = AsyncOperationListener{
            callback(it.isCompletedSucessfully)
        }
        mAsyncSession.insertOrReplace(student)
    }

    fun insertStudentList(students: List<Student>,callback:(boolean:Boolean)->Unit){
        val mAsyncSession = GreenDaoManager.getAsyncDaoSession()
        mAsyncSession.listenerMainThread = AsyncOperationListener{
            callback(it.isCompletedSucessfully)
        }
        mAsyncSession.insertOrReplaceInTx(Student::class.java,students)
    }

    fun deleteStudent(student: Student,callback:(boolean:Boolean)->Unit){
        val mAsyncSession = GreenDaoManager.getAsyncDaoSession()
        mAsyncSession.listenerMainThread = AsyncOperationListener{
            callback(it.isCompletedSucessfully)
        }
        mAsyncSession.delete(student)
    }

    fun queryStudent(id: Long,callback:(student:Student?)->Unit){
        val mAsyncSession = GreenDaoManager.getAsyncDaoSession()
        mAsyncSession.listenerMainThread = AsyncOperationListener{
            if(it.isCompletedSucessfully && it.result!=null){
                callback(it.result as Student)
            }else{
                callback(null)
            }
        }
        val query = GreenDaoManager.daoSession.studentDao.queryBuilder()
            .where(StudentDao.Properties.Id.eq(id)).build()
        mAsyncSession.queryUnique(query)
    }

    fun queryAllStudent(callback:(students:MutableList<Student>?)->Unit){
        val mAsyncSession = GreenDaoManager.getAsyncDaoSession()
        mAsyncSession.listenerMainThread = AsyncOperationListener{
            if(it.isCompletedSucessfully && it.result!=null){
                callback(it.result as MutableList<Student>)
            }else{
                callback(null)
            }
        }
        mAsyncSession.loadAll(Student::class.java)
    }

}