package org.d3if00001.storyapp.presentations.utils

import androidx.recyclerview.widget.DiffUtil
import org.d3if00001.storyapp.data.remote.retrofit.result.StoryResult

class StoryDiffUtil : DiffUtil.ItemCallback<StoryResult>() {
    override fun areItemsTheSame(oldItem: StoryResult, newItem: StoryResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StoryResult, newItem: StoryResult): Boolean {
        return oldItem == newItem
    }
}