package org.d3if00001.storyapp.data.remote.retrofit.result


import com.google.gson.annotations.SerializedName
import java.io.File

data class StoryResult(
    @SerializedName("createdAt")
    val createdAt: String?=null,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("photo")
    val photo: File
)