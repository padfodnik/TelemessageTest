package com.lizogov.telemessagetest

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "student_table")
data class Student(@PrimaryKey(autoGenerate = true) val id: Int, var name: String, val img: Int, var averageScore: Int)
