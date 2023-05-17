package org.d3if00001.storyapp.presentations.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if00001.storyapp.data.local.room.entity.User
import org.d3if00001.storyapp.domain.models.repository.DataStoreRepository
import org.d3if00001.storyapp.domain.models.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    fun getUser(id: Int): User {
        return userRepository.getUser(id)
    }
}