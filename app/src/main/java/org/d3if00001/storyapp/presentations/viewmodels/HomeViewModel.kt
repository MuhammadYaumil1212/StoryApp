package org.d3if00001.storyapp.presentations.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if00001.storyapp.data.local.preferences.UserPreferencesImpl
import org.d3if00001.storyapp.domain.models.User

class HomeViewModel(context: Context) : ViewModel() {
    private val userRepository:UserPreferencesImpl= UserPreferencesImpl(context)


}