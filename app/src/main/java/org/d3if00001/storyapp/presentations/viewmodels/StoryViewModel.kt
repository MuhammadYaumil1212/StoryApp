package org.d3if00001.storyapp.presentations.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.internal.format
import org.d3if00001.storyapp.data.remote.retrofit.APIConfig
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.response.GetAllStoriesResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetDetailResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.DetailResult
import org.d3if00001.storyapp.data.remote.retrofit.result.StoryResult
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository): ViewModel() {
    private val status = MutableLiveData<APIService.ApiStatus>()
    fun getStatus(): LiveData<APIService.ApiStatus> = status
    private var _authToken:MutableLiveData<String> = MutableLiveData()

    private val _getAllStories:MutableLiveData<List<getStoryResult>?> = MutableLiveData()
    val getAllStories:LiveData<List<getStoryResult>?> = _getAllStories

    private val _getDetailStory:MutableLiveData<DetailResult> = MutableLiveData()
    val getDetailStory:LiveData<DetailResult> = _getDetailStory

    private var _getNameUser:MutableLiveData<String> = MutableLiveData()
    val getNameUser:LiveData<String> = _getNameUser

    private var _listLocationStory:MutableLiveData<List<getStoryResult>?> = MutableLiveData()
    val listLocationStory:LiveData<List<getStoryResult>?> = _listLocationStory


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
            _authToken.value = dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY)
        }
        val clientApi = APIConfig.getApiService().getAllStories(
            Bearer = "Bearer ${_authToken.value}",
            location = 1
        )
        status.postValue(APIService.ApiStatus.LOADING)
        clientApi.enqueue(object : Callback<GetAllStoriesResponse> {
            override fun onResponse(
                call: Call<GetAllStoriesResponse>,
                response: Response<GetAllStoriesResponse>
            ) {
               if(response.isSuccessful){
                   val listResponse = response.body()?.listStory
                   _listLocationStory.postValue(listResponse)
                    status.postValue(APIService.ApiStatus.SUCCESS)
               }else{
                   status.postValue(APIService.ApiStatus.FAILED)
               }

            }
            override fun onFailure(call: Call<GetAllStoriesResponse>, t: Throwable) {
                status.postValue(APIService.ApiStatus.FAILED)
            }
        })
    }
    fun getAllStories(){
        runBlocking {
            _authToken.value = dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY)
            _getNameUser.value = dataStoreRepository.getName(AuthenticationViewModel.USER_KEY)
        }
        val clientApi = APIConfig.getApiService().getAllStories(
            Bearer = "Bearer ${_authToken.value}",
            location = 0
        )
        status.postValue(APIService.ApiStatus.LOADING)
        clientApi.enqueue(object : Callback<GetAllStoriesResponse>{
            override fun onResponse(
                call: Call<GetAllStoriesResponse>,
                response: Response<GetAllStoriesResponse>
            ) {
                val responseBody = response.body()
                val resBody = responseBody?.listStory
                if(resBody?.isNotEmpty() == true){
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