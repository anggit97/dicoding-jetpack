package com.anggit97.mynotesapps.helper

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.anggit97.mynotesapps.database.Note


/**
 * Created by Anggit Prayogo on 2019-09-06.
 * Github : @anggit97
 */

class NoteDiffCallback(private val mOldNoteList: List<Note>, private val mNewNoteList: List<Note>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldNoteList.size
    }

    override fun getNewListSize(): Int {
        return mNewNoteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].id === mNewNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldNoteList[oldItemPosition]
        val newEmployee = mNewNoteList[newItemPosition]

        return oldEmployee.title.equals(newEmployee.title)
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}