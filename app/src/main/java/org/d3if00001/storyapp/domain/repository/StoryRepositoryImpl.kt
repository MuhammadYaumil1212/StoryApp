package org.d3if00001.storyapp.domain.repository

import android.accounts.NetworkErrorException
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
import org.d3if00001.storyapp.data.StoryPagingSource
import org.d3if00001.storyapp.data.remote.retrofit.APIConfig
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.ApiResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetAllStoriesResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetDetailResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.DetailResult
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import org.d3if00001.storyapp.presentations.viewmodels.AddStoryViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val dataStoreRepository: DataStoreRepository
):StoryRepository{

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
        return APIConfig.getApiService().getDetailStories(
            Bearer="Bearer ${dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY)}",
            id = id
        )
    }

    override suspend fun mapStory(): GetAllStoriesResponse {
        return APIConfig.getApiService().getAllStories(
            Bearer = "Bearer ${dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY)}",
            location = 1,
        )
    }
}