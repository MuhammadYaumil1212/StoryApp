package org.d3if00001.storyapp.data.remote.retrofit

import okhttp3.MultipartBody
import org.d3if00001.storyapp.data.remote.retrofit.response.AddNewStoryResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetAllStoriesResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.LoginResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.RegisterResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.LoginResult
import org.d3if00001.storyapp.data.remote.retrofit.result.RegisterResult
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface APIService {
    enum class ApiStatus { LOADING, SUCCESS, FAILED }
    @POST("login")
    fun authentication(@Body loginResult: LoginResult):Call<LoginResponse>
    @POST("register")
    fun register(@Body registerResult:RegisterResult):Call<RegisterResponse>

    @Multipart

    @POST("stories")
    fun addNewStory(
        @Header("Authorization")Bearer:String,
        @Part("description")description:String,
        @Part file: MultipartBody.Part,
    ):Call<AddNewStoryResponse>
    @GET("stories")
    fun getAllStories(@Header("Authorization")Bearer: String,@Query("location")location:Int):Call<GetAllStoriesResponse>
}