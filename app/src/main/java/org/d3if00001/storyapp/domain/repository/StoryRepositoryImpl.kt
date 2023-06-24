package org.d3if00001.storyapp.domain.repository

import android.accounts.NetworkErrorException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Message
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if00001.storyapp.data.StoryPagingSource
import org.d3if00001.storyapp.data.remote.retrofit.APIConfig
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.ApiResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.AddNewStoryResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetAllStoriesResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetDetailResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.AddNewStoryResult
import org.d3if00001.storyapp.data.remote.retrofit.result.DetailResult
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import org.d3if00001.storyapp.presentations.viewmodels.AddStoryViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val dataStoreRepository: DataStoreRepository
):StoryRepository{
    companion object{
        private const val MAXIMAL_SIZE = 1000000
    }
    override fun getStory(): Flow<PagingData<getStoryResult>> {
        val pagingSource = StoryPagingSource(
            dataStore = dataStoreRepository,
            apiService = apiService
        )
        return Pager(
            config = PagingConfig(pageSize = 5),
        ){ pagingSource }.flow
    }

    override suspend fun detailStory(id: String):GetDetailResponse {
        return apiService.getDetailStories(
            Bearer="Bearer ${dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY)}",
            id = id
        )
    }

    override suspend fun mapStory(): GetAllStoriesResponse {
        return apiService.getAllStories(
            Bearer = "Bearer ${dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY)}",
            location = 1,
        )
    }
    private fun reduceImageSize(file:File):File{
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength:Int
        do{
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        }while (streamLength > MAXIMAL_SIZE)
        bitmap.compress(Bitmap.CompressFormat.JPEG,compressQuality, FileOutputStream(file))
        return file
    }
    override suspend fun uploadImageRequest(
        description:String,
        file: File
    ):Call<AddNewStoryResponse>{
        val fileData = reduceImageSize(file)
        val requestImageFile = fileData.asRequestBody("image/jpeg".toMediaType())
        val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            fileData.name,
            requestImageFile
        )
        return apiService.addNewStory(
            Bearer = "Bearer ${dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY)}",
            description = description.toRequestBody(),
            file = imageMultiPart
        )
    }
}