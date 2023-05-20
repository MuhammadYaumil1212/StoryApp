package org.d3if00001.storyapp.domain.models.repository

import kotlinx.coroutines.flow.Flow
import org.d3if00001.storyapp.data.local.room.entity.User

interface DataStoreRepository {
    suspend fun setLoggedIn(key:String,loggedIn:Boolean)
    suspend fun getLoggedIn(key:String):Boolean?
    suspend fun clearData(key: String)

    suspend fun login(key: String,value:String)
    suspend fun register(key: String,value:String)
}