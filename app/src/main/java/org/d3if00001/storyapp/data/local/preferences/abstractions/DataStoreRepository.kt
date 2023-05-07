package org.d3if00001.storyapp.data.local.preferences.abstractions

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getAuthToken(key: String) : Flow<String?>
    suspend fun putAuthToken(key: String, authToken:String)
    suspend fun clearData(key: String)

}