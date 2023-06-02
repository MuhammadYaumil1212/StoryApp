package org.d3if00001.storyapp.data.remote.retrofit.result


import com.google.gson.annotations.SerializedName

data class StoryResult(
    @SerializedName("createdAt")
    val createdAt: String, // 2022-01-08T06:34:18.598Z
    @SerializedName("description")
    val description: String, // Lorem Ipsum
    @SerializedName("id")
    val id: String, // story-FvU4u0Vp2S3PMsFg
    @SerializedName("name")
    val name: String, // Dimas
    @SerializedName("photoUrl")
    val photoUrl: String // https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png
)