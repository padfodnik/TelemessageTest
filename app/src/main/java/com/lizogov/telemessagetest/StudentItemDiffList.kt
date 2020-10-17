package com.lizogov.telemessagetest

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil

class StudentItemDiffList(
    private val oldList: List<Student>,
    private val newList: List<Student>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, name, _,avrScore) = oldList[oldPosition]
        val (_, name1, _,avrScore1) = newList[newPosition]

        return name == name1 && avrScore == avrScore1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}