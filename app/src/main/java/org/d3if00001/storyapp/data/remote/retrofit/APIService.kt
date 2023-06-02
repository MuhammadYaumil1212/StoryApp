package org.d3if00001.storyapp.data.remote.retrofit

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
    @FormUrlEncoded
    @Multipart
    @POST("stories")
    fun addNewStory(
        @Part("description")description:String,
        @Part("photo")photo:File
    ):Call<AddNewStoryResponse>
    @GET("stories")
    fun getAllStories():List<GetAllStoriesResponse>
}