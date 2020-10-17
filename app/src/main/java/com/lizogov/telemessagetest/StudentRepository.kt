
package com.lizogov.telemessagetest

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData


class StudentRepository(private val studentDao: StudentDao) {


    val allStudents: LiveData<List<Student>> = studentDao.getAllStudents()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(student: Student) {
        studentDao.insert(student)
    }

    fun delete(student: Student) {
        studentDao.delete(student)
    }

}
