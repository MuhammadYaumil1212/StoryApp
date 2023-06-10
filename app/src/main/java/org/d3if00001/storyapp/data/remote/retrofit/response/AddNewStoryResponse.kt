package org.d3if00001.storyapp.data.remote.retrofit.response


import com.google.gson.annotations.SerializedName
import org.d3if00001.storyapp.data.remote.retrofit.result.AddNewStoryResult
import org.d3if00001.storyapp.data.remote.retrofit.result.LoginResult

data class AddNewStoryResponse(
    @SerializedName("error")
    val error: Boolean, // false

    @SerializedName("message")
    val message: String // success
)