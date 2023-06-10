package org.d3if00001.storyapp.presentations.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.d3if00001.storyapp.data.remote.retrofit.APIConfig
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.response.AddNewStoryResponse
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) : ViewModel() {
    private val status = MutableLiveData<APIService.ApiStatus>()
    private var _getFile: File? = null
    private var _descriptionText:MutableLiveData<String?> = MutableLiveData()
    private var _authToken:MutableLiveData<String> = MutableLiveData()

    companion object{
        const val TOKEN_KEY = "token_key"
        private const val MAXIMAL_SIZE = 1000000
    }
    fun getStatus(): LiveData<APIService.ApiStatus> = status
    fun setFile(file:File){_getFile = file}
    fun setDescription(description:String){_descriptionText.value = description}
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
        bitmap.compress(Bitmap.CompressFormat.JPEG,compressQuality,FileOutputStream(file))
        return file
    }
    fun uploadImage(){
        if(_getFile != null){
            val file = reduceImageSize(_getFile as File)
            val description = _descriptionText.value
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            runBlocking {
                _authToken.value = dataStoreRepository.getToken(TOKEN_KEY)
            }
            Log.i("token key","key : ${_authToken.value}")
            val apiService = APIConfig.getApiService()
            val uploadImageRequest = apiService.addNewStory(
                description = description.toString(),
                file = imageMultiPart,
                Bearer = "Bearer ${_authToken.value!!}"
            )
            status.postValue(APIService.ApiStatus.LOADING)
            uploadImageRequest.enqueue(object : Callback<AddNewStoryResponse> {
                override fun onResponse(
                    call: Call<AddNewStoryResponse>,
                    response: Response<AddNewStoryResponse>
                ) {
                    val responseBody = response.body()
                    if(responseBody!=null && !responseBody.error){
                        status.postValue(APIService.ApiStatus.SUCCESS)
                        Log.i("Success response", responseBody.message)
                    }else{
                        status.postValue(APIService.ApiStatus.FAILED)
                        Log.e("error response body","${responseBody?.error}")
                    }
                }

                override fun onFailure(call: Call<AddNewStoryResponse>, t: Throwable) {
                    status.postValue(APIService.ApiStatus.FAILED)
                    Log.e("error throw","${t.message}")
                }
            })

        }else{
            status.postValue(APIService.ApiStatus.FAILED)
            Log.e("Error input","file is null")
        }
    }
}