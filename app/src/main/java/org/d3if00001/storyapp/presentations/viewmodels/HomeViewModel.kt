package org.d3if00001.storyapp.presentations.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DataStoreRepository): ViewModel() {
    private val _setToken : MutableLiveData<String> = MutableLiveData<String>()
    val getToken : LiveData<String> = _setToken

    private val _isLoggedIn : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isLoggedIn : LiveData<Boolean> = _isLoggedIn
    companion object{
        private const val AUTHKEY = "key_auth"
        private const val getId = "key_id"
    }
    fun setAuthToken(value : String?) = viewModelScope.launch {
        repository.putAuthToken(AUTHKEY,value.toString())
    }
    fun getAuthToken() = viewModelScope.launch {
        repository.getAuthToken(AUTHKEY).collect{ token->
            _setToken.value = token
            _isLoggedIn.value = token != null
        }
    }
    fun logout() = runBlocking {
        _isLoggedIn.value = false
        repository.clearData(AUTHKEY)
    }
}