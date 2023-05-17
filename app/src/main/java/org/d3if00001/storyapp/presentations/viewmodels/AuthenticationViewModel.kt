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
    private val _getUserId : MutableLiveData<Int> = MutableLiveData<Int>()
    val getUserId : LiveData<Int> = _getUserId
    companion object{
        const val ISLOGGEDIN = "is_loggedIn"
    }

    fun registerAccount(user:User){
         userRepository.registerAccount(user)
        _getUserId.value = user.id
    }

    fun getLoggedIn():Boolean?= runBlocking {
        return@runBlocking dataStoreRepository.getLoggedIn(ISLOGGEDIN)
    }

    fun authenticate(email: String, password: String): User? {
        viewModelScope.launch {
            dataStoreRepository.setLoggedIn(key = ISLOGGEDIN,loggedIn = true)
        }
        return userRepository.verifyLogin(email, password)
    }
    fun logout() = runBlocking {
        dataStoreRepository.clearData(ISLOGGEDIN)
    }
}