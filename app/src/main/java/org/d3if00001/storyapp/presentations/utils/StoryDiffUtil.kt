package org.d3if00001.storyapp.presentations.utils

import androidx.recyclerview.widget.DiffUtil
import org.d3if00001.storyapp.data.remote.retrofit.result.StoryResult
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult

class StoryDiffUtil(private val mOldStoryList:List<getStoryResult>, private val newStoryList:List<getStoryResult>)
    :DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldStoryList.size

    override fun getNewListSize(): Int = newStoryList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)
            : Boolean = mOldStoryList[oldItemPosition].id == newStoryList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHoliday = mOldStoryList[oldItemPosition]
        val newHoliday = newStoryList[newItemPosition]
        return oldHoliday.id == newHoliday.id
    }
}

