package org.d3if00001.storyapp.data.remote.retrofit.response


import com.google.gson.annotations.SerializedName
import org.d3if00001.storyapp.data.remote.retrofit.result.RegisterResult

data class RegisterResponse(
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("loginResult")
    val RegisterResult: RegisterResult,
    @SerializedName("message")
    val message: String // success
)