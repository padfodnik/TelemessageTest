package com.lizogov.telemessagetest

import android.content.Context
import android.graphics.Color
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random.Default.nextInt


@Database(entities = [Student::class], version = 1)
abstract class StudentRoomDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao


    companion object {
        @Volatile
        private var INSTANCE: StudentRoomDatabase? = null


        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): StudentRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentRoomDatabase::class.java,
                    "student_database"
                )

                    .fallbackToDestructiveMigration()
                    .addCallback(StudentDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class StudentDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        val res = database.studentDao().getStudents()
                        if (res.isEmpty()) {
                            populateDatabase(database.studentDao())
                        }
                        updateDatabase(database.studentDao())
                    }
                }

            }
        }

        fun populateDatabase(studentDao: StudentDao) {

            studentDao.deleteAll()
            val list = arrayListOf<Student>()
            list.add(Student(1, "Max", Color.CYAN, 89))
            list.add(Student(2, "Avraam", Color.GREEN, 74))
            list.add(Student(3, "Slava", Color.MAGENTA, 75))
            list.add(Student(4, "Victor", Color.RED, 95))
            list.add(Student(5, "Olivia", Color.YELLOW, 98))
            studentDao.insertAll(list)

        }

        fun updateDatabase(studentDao: StudentDao) {
            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    val res = studentDao.getStudents()
                    if (res.isNotEmpty()){
                        for (a in res) {
                            a.averageScore = nextInt(101)
                        }
                    }

                    studentDao.deleteAll()
                    studentDao.insertAll(res)
                }
            }, 0, 5000)

        }
    }

}
