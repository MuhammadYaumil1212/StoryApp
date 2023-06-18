package org.d3if00001.storyapp.data.remote.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if00001.storyapp.data.remote.retrofit.response.AddNewStoryResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetAllStoriesResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetDetailResponse
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
    suspend fun authentication(@Body loginResult: LoginResult):LoginResponse
    @POST("register")
    suspend fun register(@Body registerResult:RegisterResult):RegisterResponse

    @Multipart
    @POST("stories")
    fun addNewStory(
        @Header("Authorization")Bearer:String,
            @Part("description")description:RequestBody,
        @Part file: MultipartBody.Part,
    ):Call<AddNewStoryResponse>
    @GET("stories")
    fun getAllStories(@Header("Authorization")Bearer: String,@Query("location")location:Int):Call<GetAllStoriesResponse>
    @GET("stories/{id}")
    fun getDetailStories(@Header("Authorization")Bearer: String,@Path("id")id:String):Call<GetDetailResponse>
}