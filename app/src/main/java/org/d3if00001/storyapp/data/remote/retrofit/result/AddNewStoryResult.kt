package org.d3if00001.storyapp.data.remote.retrofit.result

import com.google.gson.annotations.SerializedName
import java.io.File

data class AddNewStoryResult(
    @field:SerializedName("description")
    val description:String,
    @field:SerializedName("photo")
    val photo:File
)
