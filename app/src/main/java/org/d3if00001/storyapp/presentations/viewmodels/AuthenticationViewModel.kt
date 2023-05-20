package org.d3if00001.storyapp.presentations.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if00001.storyapp.data.local.room.entity.User
import org.d3if00001.storyapp.data.repository.UserRepositoryImpl
import org.d3if00001.storyapp.domain.models.repository.DataStoreRepository

import org.d3if00001.storyapp.domain.models.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private var userRepository: UserRepository,
    private var dataStoreRepository: DataStoreRepository
    ):ViewModel() {
    companion object{
        const val ISLOGGEDIN = "is_loggedIn"
        const val LOGIN = "login"
        const val REGISTER = "register"
    }

   fun registerAccount(value:String)= runBlocking {
       dataStoreRepository.register(REGISTER,value)
   }

    fun loginAccount(value: String)= runBlocking {
        dataStoreRepository.login(LOGIN,value)
    }

    fun getLoggedIn():Boolean?= runBlocking {
        return@runBlocking dataStoreRepository.getLoggedIn(ISLOGGEDIN)
    }
    fun setLoggedIn(value: Boolean) = runBlocking {
        dataStoreRepository.setLoggedIn(ISLOGGEDIN,value)
    }
    fun logout() = runBlocking {
        dataStoreRepository.clearData(ISLOGGEDIN)
        dataStoreRepository.clearData(REGISTER)
        dataStoreRepository.clearData(LOGIN)
    }
}