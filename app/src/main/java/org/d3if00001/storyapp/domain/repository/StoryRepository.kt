package org.d3if00001.storyapp.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if00001.storyapp.data.remote.retrofit.ApiResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.AddNewStoryResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetAllStoriesResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetDetailResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import retrofit2.Call
import java.io.File

interface StoryRepository {
    fun getStory():Flow<PagingData<getStoryResult>>
    suspend fun detailStory(id:String):GetDetailResponse
    suspend fun mapStory():GetAllStoriesResponse
    suspend fun uploadImageRequest(
        description:String,
        file: File
    ):Call<AddNewStoryResponse>
}
