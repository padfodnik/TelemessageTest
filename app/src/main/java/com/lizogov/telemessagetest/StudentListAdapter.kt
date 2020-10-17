package com.lizogov.telemessagetest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class StudentListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var students = emptyList<Student>()

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studNameItemView: TextView = itemView.findViewById(R.id.item_student_name)
        val studImgItemView: ImageView = itemView.findViewById(R.id.item_student_image)
        val studAvrScoreItemView: TextView = itemView.findViewById(R.id.item_student_avrScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val current = students[position]
        holder.studNameItemView.text = current.name
        holder.studImgItemView.setBackgroundColor(current.img)
        holder.studAvrScoreItemView.text = current.averageScore.toString()
    }

    internal fun setStudents(students: List<Student>) {

        val diffCallback = StudentItemDiffList(this.students, students)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.students = emptyList()
        this.students = students
//        notifyDataSetChanged()
        diffResult.dispatchUpdatesTo(this)

    }

    override fun getItemCount() = students.size

    fun getItem(pos: Int): Student {
        return this.students[pos]
    }


}


