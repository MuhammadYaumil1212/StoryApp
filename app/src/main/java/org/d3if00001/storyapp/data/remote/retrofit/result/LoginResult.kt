package org.d3if00001.storyapp.data.remote.retrofit.result


import com.google.gson.annotations.SerializedName

data class LoginResult(
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("name")
    val name: String?=null,
    @field:SerializedName("token")
    val token: String?=null,
    @field:SerializedName("password")
    val password: String
)