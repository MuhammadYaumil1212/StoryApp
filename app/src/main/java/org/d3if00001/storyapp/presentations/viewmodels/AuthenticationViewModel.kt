package org.d3if00001.storyapp.presentations.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if00001.storyapp.data.remote.retrofit.APIConfig
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.response.LoginResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.RegisterResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.LoginResult
import org.d3if00001.storyapp.data.remote.retrofit.result.RegisterResult
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private var dataStoreRepository: DataStoreRepository):ViewModel() {
    private val status = MutableLiveData<APIService.ApiStatus>()

    private var _getDataToken:MutableLiveData<String?> = MutableLiveData()
    val getDataToken:LiveData<String?> = _getDataToken

    private var _getNameUser:MutableLiveData<String> = MutableLiveData()
    val getNameUser:LiveData<String> = _getNameUser
    companion object{
        const val TOKEN_KEY = "token_key"
        const val USER_KEY = "user_key"
    }
    fun getToken() = viewModelScope.launch {
        _getDataToken.value = dataStoreRepository.getToken(TOKEN_KEY)
    }
    fun getName() = viewModelScope.launch {
        _getNameUser.value = dataStoreRepository.getName(USER_KEY)
    }
    fun getStatus(): LiveData<APIService.ApiStatus> = status
    fun authentication(email:String,password: String){
        val loginModel = LoginResult(email = email, password = password)
        val client = APIConfig.getApiService().authentication(loginModel)
        status.postValue(APIService.ApiStatus.LOADING)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                val loginToken = responseBody?.loginResult?.token
                val loginName = responseBody?.loginResult?.name
                if(response.isSuccessful){
                    Log.d("success response", responseBody.toString())
                    runBlocking {
                        if (loginToken != null && loginName != null) {
                            dataStoreRepository.setToken(TOKEN_KEY,loginToken)
                            dataStoreRepository.setToken(USER_KEY,loginName)
                            status.postValue(APIService.ApiStatus.SUCCESS)
                        }
                    }

                }else{
                    status.postValue(APIService.ApiStatus.FAILED)
                    Log.e("response null", response.message())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                status.postValue(APIService.ApiStatus.FAILED)
                Log.e("response failure","${t.message}")
            }

        })
    }
    fun register(name:String,email:String,password: String){
        val registerModel = RegisterResult(name = name, email = email, password = password)
        val client = APIConfig.getApiService().register(registerModel)
        status.postValue(APIService.ApiStatus.LOADING)
        client.enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                val resBody = responseBody?.RegisterResult
                if(resBody == null){
                    status.postValue(APIService.ApiStatus.FAILED)
                    Log.e("not found","Account not found")
                }
                status.postValue(APIService.ApiStatus.SUCCESS)
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("response failure","${t.message}")
            }

        })
    }
    fun logout() = runBlocking {
        dataStoreRepository.clearData(TOKEN_KEY)
        dataStoreRepository.clearData(USER_KEY)
    }
}