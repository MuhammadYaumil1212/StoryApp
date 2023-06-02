package org.d3if00001.storyapp.data.remote.retrofit.result

import com.google.gson.annotations.SerializedName

data class RegisterResult(
    @field:SerializedName("email")
    val email: String, // yaumil@gmail.com
    @field:SerializedName("name")
    val name: String, // yaumil
    @field:SerializedName("password")
    val password: String // password
)
