package org.d3if00001.storyapp.data.remote.retrofit.result


import com.google.gson.annotations.SerializedName
import java.io.File

data class getStoryResult(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("photoUrl")
    val photo: String,
    @SerializedName("lat")
    val lat:Double?=null,
    @SerializedName("lon")
    val lon:Double?=null
)