package org.d3if00001.storyapp.data.remote

import com.google.gson.annotations.SerializedName
import org.d3if00001.storyapp.data.database.Item.StoryResponseItem

data class StoryMediator(
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("listStory")
    val listStory: List<StoryResponseItem>,
    @SerializedName("message")
    val message: String // Stories fetched successfully
)