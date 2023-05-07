package org.d3if00001.storyapp.presentations.viewmodels

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if00001.storyapp.data.local.preferences.abstractions.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DataStoreRepository): ViewModel() {
    private val _setToken : MutableLiveData<String> = MutableLiveData<String>()
    val getToken : LiveData<String> = _setToken
    companion object{
        private const val AUTHKEY = "key_auth"
    }
    fun setAuthToken(value : String?) = viewModelScope.launch {
        repository.putAuthToken(AUTHKEY,value.toString())
    }
    fun getAuthToken() = viewModelScope.launch {
        repository.getAuthToken(AUTHKEY).collect{
            token->_setToken.value = token
        }
    }
    fun logout() = runBlocking { repository.clearData(AUTHKEY) }
}