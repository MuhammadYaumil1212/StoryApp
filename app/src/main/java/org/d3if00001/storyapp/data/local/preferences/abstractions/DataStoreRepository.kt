package org.d3if00001.storyapp.data.local.preferences.abstractions

interface DataStoreRepository {
    suspend fun putName(key:String,value:String)
    suspend fun putEmail(key: String,value: String)
    suspend fun putPassword(key: String,value: String)
    suspend fun getName(key:String):String?
    suspend fun getEmail(key:String):String?
    suspend fun getPassword(key:String):String?
    suspend fun clearData(key:String)

}