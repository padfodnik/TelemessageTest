package com.lizogov.telemessagetest

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface StudentDao {


    @Query("SELECT * from student_table ORDER BY averageScore DESC")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("SELECT * from student_table ORDER BY averageScore DESC")
    fun getStudents(): List<Student>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(student: Student)

    @Query("DELETE FROM student_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(students: Collection<Student>)

    @Update
    fun updateItem(student: Student?)

    @Delete
    fun delete(student: Student)

}
