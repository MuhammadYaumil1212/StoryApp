package org.d3if00001.storyapp.presentations.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.d3if00001.storyapp.data.local.room.entity.User
import org.d3if00001.storyapp.data.repository.UserRepositoryImpl

import org.d3if00001.storyapp.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private var userRepository: UserRepository):ViewModel() {
    private  val _isveryfied : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isVeryfied : LiveData<Boolean> = _isveryfied
    fun registerAccount(user:User){
        viewModelScope.launch {
            try { userRepository.registerAccount(user) }
            catch (e:java.lang.Exception){
                Log.e("error exception","${e.message}")
            }
        }
    }

    fun authenticate(email: String, password: String): User? {
        return userRepository.verifyLogin(email, password)
    }
}