package org.d3if00001.storyapp.data.remote.retrofit.response


import com.google.gson.annotations.SerializedName
import org.d3if00001.storyapp.data.remote.retrofit.result.StoryResult
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult

data class GetAllStoriesResponse(
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("listStory")
    val listStory: List<getStoryResult>,
    @SerializedName("message")
    val message: String // Stories fetched successfully
)