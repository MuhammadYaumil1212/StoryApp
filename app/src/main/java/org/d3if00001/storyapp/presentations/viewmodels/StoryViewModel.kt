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
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    storyRepository: StoryRepository
): ViewModel() {
    private val status = MutableLiveData<APIService.ApiStatus>()
    fun getStatus(): LiveData<APIService.ApiStatus> = status
    private var _authToken:MutableLiveData<String> = MutableLiveData()

    private val _getDetailStory:MutableLiveData<DetailResult> = MutableLiveData()
    val getDetailStory:LiveData<DetailResult> = _getDetailStory

    private var _getNameUser:MutableLiveData<String> = MutableLiveData()
    val getNameUser:LiveData<String> = _getNameUser

    private var _getMapStories:MutableLiveData<ApiResponse<GetAllStoriesResponse>> = MutableLiveData()
    val getMapStories:LiveData<ApiResponse<GetAllStoriesResponse>> get() = _getMapStories
    val story:Flow<PagingData<getStoryResult>> = storyRepository.getStory().cachedIn(viewModelScope)
    init {
        viewModelScope.launch {
            _getNameUser.value = dataStoreRepository.getName(AuthenticationViewModel.USER_KEY)
        }
    }
    fun detailStory(id:String){
        runBlocking {
            _authToken.value = dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY)
        }
        val clientApi = APIConfig.getApiService().getDetailStories(
            Bearer="Bearer ${_authToken.value!!}",
            id = id
        )

        status.postValue(APIService.ApiStatus.LOADING)
        clientApi.enqueue(object : Callback<GetDetailResponse>{
            override fun onResponse(
                call: Call<GetDetailResponse>,
                response: Response<GetDetailResponse>
            ) {
                val responseBody = response.body()
                val responseResult = responseBody?.story
                val detailStory = DetailResult(
                    id = responseResult!!.id,
                    description = responseResult.description,
                    createdAt = responseResult.createdAt,
                    photo = responseResult.photo,
                    name = responseResult.name
                )
                _getDetailStory.value = detailStory
                status.postValue(APIService.ApiStatus.SUCCESS)
            }

            override fun onFailure(call: Call<GetDetailResponse>, t: Throwable) {
                Log.d("data failure","${t.message}")
                status.postValue(APIService.ApiStatus.FAILED)
            }

        })
    }
    fun getMapStories(){
        viewModelScope.launch {
            try {
                _authToken.value = dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY)
                val clientApi = APIConfig.getApiService().getAllStories(
                    Bearer = "Bearer ${_authToken.value}",
                    location = 1,
                )
                if(clientApi.error){
                    _getMapStories.postValue(ApiResponse.Error(clientApi.message))
                }else{
                    _getMapStories.postValue(ApiResponse.Success(clientApi))
                }
            }catch (e:Exception){
                _getMapStories.postValue(ApiResponse.Error(e.message.toString()))
            }

        }
    }

}