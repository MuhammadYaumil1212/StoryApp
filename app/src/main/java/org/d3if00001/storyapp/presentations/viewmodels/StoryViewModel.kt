package org.d3if00001.storyapp.presentations.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.Pager
import androidx.paging.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if00001.storyapp.data.StoryPagingSource
import org.d3if00001.storyapp.data.remote.retrofit.APIConfig
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.ApiResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetAllStoriesResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetDetailResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.DetailResult
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import org.d3if00001.storyapp.domain.repository.StoryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val storyRepository: StoryRepository
): ViewModel() {
    private var _getNameUser:MutableLiveData<String> = MutableLiveData()
    val getNameUser:LiveData<String> = _getNameUser
    
    private var _getMapStories:MutableLiveData<ApiResponse<GetAllStoriesResponse>> = MutableLiveData()
    val getMapStories:LiveData<ApiResponse<GetAllStoriesResponse>> get() = _getMapStories

    private val _getDetailStory: MutableLiveData<ApiResponse<GetDetailResponse>> = MutableLiveData()
    val getDetailStory:LiveData<ApiResponse<GetDetailResponse>> = _getDetailStory

    val story:Flow<PagingData<getStoryResult>> = storyRepository.getStory().cachedIn(viewModelScope)
    init {
        viewModelScope.launch {
            _getNameUser.value = dataStoreRepository.getName(AuthenticationViewModel.USER_KEY)
        }
    }
    fun getDetailStory(id:String) = viewModelScope.launch {
        try{
            _getDetailStory.postValue(ApiResponse.Loading)
            val clientApi = storyRepository.detailStory(id)
            if(clientApi.error){
                _getDetailStory.postValue(ApiResponse.Error(clientApi.message))
            }else{
                _getDetailStory.postValue(ApiResponse.Success(clientApi))
            }
        }catch (e:IOException){
            _getDetailStory.postValue(ApiResponse.Error(e.message.toString()))
        }catch (h:HttpException){
            _getDetailStory.postValue(ApiResponse.Error(h.message.toString()))
        }
    }

    fun getMapStories() = viewModelScope.launch {
        try {
            val clientApi = storyRepository.mapStory()
            if(clientApi.error){
                _getMapStories.postValue(ApiResponse.Error(clientApi.message))
            }else{
                _getMapStories.postValue(ApiResponse.Success(clientApi))
            }
        }catch (e:Exception){
            _getMapStories.postValue(ApiResponse.Error(e.message.toString()))
        }catch (h:HttpException){
            _getMapStories.postValue(ApiResponse.Error(h.message.toString()))
        }
    }


}