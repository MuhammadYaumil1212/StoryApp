package org.d3if00001.storyapp.data.remote.retrofit.response


import com.google.gson.annotations.SerializedName
import org.d3if00001.storyapp.data.remote.retrofit.result.LoginResult

data class LoginResponse(
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("loginResult")
    val loginResult: LoginResult,
    @SerializedName("message")
    val message: String // success
)