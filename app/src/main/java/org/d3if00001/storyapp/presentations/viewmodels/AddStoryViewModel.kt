package org.d3if00001.storyapp.presentations.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if00001.storyapp.data.remote.retrofit.APIConfig
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.ApiResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.AddNewStoryResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.LoginResponse
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import org.d3if00001.storyapp.domain.repository.StoryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val storyRepository: StoryRepository,
    private val apiService: APIService
) : ViewModel() {
    private var _getFile: File? = null
    private var _authToken:MutableLiveData<String> = MutableLiveData()
    private var _uploadImageResponse:MutableLiveData<ApiResponse<AddNewStoryResponse>> = MutableLiveData()
    val uploadImageResponse:LiveData<ApiResponse<AddNewStoryResponse>> get() = _uploadImageResponse

    companion object{
        const val TOKEN_KEY = "token_key"
    }
    init {
        viewModelScope.launch {
            _authToken.value = dataStoreRepository.getToken(TOKEN_KEY)
        }
    }
    fun setFile(file:File){_getFile = file}

    fun uploadImage(description:String){
        if(_getFile != null){
            viewModelScope.launch {
               val clientApi = storyRepository.uploadImageRequest(
                    description = description,
                    file = _getFile!!
                )
                _uploadImageResponse.postValue(ApiResponse.Loading)
                clientApi.enqueue(object : Callback<AddNewStoryResponse>{
                    override fun onResponse(
                        call: Call<AddNewStoryResponse>,
                        response: Response<AddNewStoryResponse>
                    ) {
                        val responseBody = response.body()
                        if(responseBody?.error == true){
                            _uploadImageResponse.postValue(ApiResponse.Error(responseBody.message))
                        }else{
                            _uploadImageResponse.postValue(ApiResponse.Empty)
                        }
                    }
                    override fun onFailure(call: Call<AddNewStoryResponse>, t: Throwable) {
                        _uploadImageResponse.postValue(ApiResponse.Error(t.message!!))
                    }
                })

            }
        }else{
            Log.e("Error input","file is null")
        }
    }
}