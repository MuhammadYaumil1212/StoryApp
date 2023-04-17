package org.d3if00001.storyapp.data.repository

import org.d3if00001.storyapp.domain.models.User

interface UserPreferenceRepository {
    fun setUser(value : User, token:String)
    fun getUser(): User
    fun isLoggedIn(): Boolean
    fun getToken() : String
    fun logout()
}