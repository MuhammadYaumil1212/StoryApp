package org.d3if00001.storyapp.domain.repository

interface DataStoreRepository {
    suspend fun clearData(key: String)
    suspend fun setToken(key:String,value: String)
    suspend fun getToken(key: String):String

    suspend fun setName(key:String,value: String)
    suspend fun getName(key:String):String
}