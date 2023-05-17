package org.d3if00001.storyapp.domain.models.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun setLoggedIn(key:String,loggedIn:Boolean)
    suspend fun getLoggedIn(key:String):Boolean?
    suspend fun clearData(key: String)
}