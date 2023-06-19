package org.d3if00001.storyapp.data.local.preferences.implementations

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import javax.inject.Inject


private const val PREFERENCES_NAME = "User_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
class DataStoreRepositoryImpl @Inject constructor(private val context: Context)  :
    DataStoreRepository {
    override suspend fun clearData() {
        context.dataStore.edit { preferences-> preferences.clear()}
    }

    override suspend fun setToken(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences->preferences[preferencesKey] = value}
    }

    override suspend fun getToken(key: String): String? {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey]
    }

    override suspend fun setName(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences->preferences[preferencesKey] = value}
    }

    override suspend fun getName(key: String): String {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey]?:""
    }

}