package org.d3if00001.storyapp.presentations.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import org.d3if00001.storyapp.data.remote.retrofit.APIConfig
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.response.GetAllStoriesResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.StoryResult
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository): ViewModel() {
    private val status = MutableLiveData<APIService.ApiStatus>()
    fun getStatus(): LiveData<APIService.ApiStatus> = status
    private var _authToken:MutableLiveData<String> = MutableLiveData()

    private val _getAllStories:MutableLiveData<List<StoryResult>?> = MutableLiveData()
    val getAllStories:LiveData<List<StoryResult>?> = _getAllStories

    fun getAllStories(){
        runBlocking {
            _authToken.value = dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY)
        }
        val clientApi = APIConfig.getApiService().getAllStories(
            Bearer = "Bearer ${_authToken.value!!}",
            location = 1
        )
        status.postValue(APIService.ApiStatus.LOADING)
        clientApi.enqueue(object : Callback<GetAllStoriesResponse>{
            override fun onResponse(
                call: Call<GetAllStoriesResponse>,
                response: Response<GetAllStoriesResponse>
            ) {
                val responseBody = response.body()
                val resBody = responseBody?.listStory
                if(resBody!!.isNotEmpty()){
                    _getAllStories.value = resBody
                    status.postValue(APIService.ApiStatus.SUCCESS)
                }else{
                    status.postValue(APIService.ApiStatus.FAILED)
                    Log.e("error resBody","response body is null")
                }
            }

            override fun onFailure(call: Call<GetAllStoriesResponse>, t: Throwable) {
                status.postValue(APIService.ApiStatus.FAILED)
                Log.e("failure response","${t.message}")
            }

        })
    }
}