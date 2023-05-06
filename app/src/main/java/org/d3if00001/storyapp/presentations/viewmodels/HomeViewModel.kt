package org.d3if00001.storyapp.presentations.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if00001.storyapp.data.local.preferences.abstractions.DataStoreRepository
import org.d3if00001.storyapp.data.local.preferences.implementations.DataStoreRepositoryImpl
import org.d3if00001.storyapp.domain.models.User
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DataStoreRepository): ViewModel() {
    companion object{
        private const val NAME = "name_key"
        private const val EMAIL = "email_key"
        private const val PASSWORD = "password_key"
    }
    private val _isSession = MutableLiveData<Boolean>()
    val isSession:LiveData<Boolean> = _isSession
    fun setName(value : String?) = viewModelScope.launch { repository.putName(NAME,value.toString()) }
    fun getName() : String? = runBlocking { repository.getName(NAME) }
    fun logout() = runBlocking { repository.clearData(NAME) }

//    fun setSession(user: User){
//        viewModelScope.launch {
//            repository.putName(NAME,user.name.toString())
//            repository.putEmail(EMAIL,user.email.toString())
//            repository.putPassword(PASSWORD,user.password.toString())
//        }
//    }
}