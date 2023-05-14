package org.d3if00001.storyapp.data.local.preferences.implementations

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import javax.inject.Inject


private const val PREFERENCES_NAME = "User_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
class DataStoreRepositoryImpl @Inject constructor(private val context: Context)  :
    DataStoreRepository {
    override fun getAuthToken(key: String): Flow<String?> {
        val preferencesKey = stringPreferencesKey(key)
        return context.dataStore.data.map { preferences->preferences[preferencesKey] }
    }

    override suspend fun putAuthToken(key: String, authToken: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences -> preferences[preferencesKey] = authToken }
    }

    override suspend fun clearData(key: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences-> preferences.remove(preferencesKey) }
    }

}