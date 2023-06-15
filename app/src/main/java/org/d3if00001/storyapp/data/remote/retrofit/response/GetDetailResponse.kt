package org.d3if00001.storyapp.data.remote.retrofit.response

import com.google.gson.annotations.SerializedName
import org.d3if00001.storyapp.data.remote.retrofit.result.DetailResult

data class GetDetailResponse(
    @SerializedName("error")
    val error:Boolean,
    @SerializedName("message")
    val message:String,
    @SerializedName("story")
    val story:DetailResult
)
