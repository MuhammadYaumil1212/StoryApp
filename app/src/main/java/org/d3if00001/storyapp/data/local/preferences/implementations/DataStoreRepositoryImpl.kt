package org.d3if00001.storyapp.data.local.preferences.implementations

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.d3if00001.storyapp.domain.models.repository.DataStoreRepository
import javax.inject.Inject


private const val PREFERENCES_NAME = "User_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
class DataStoreRepositoryImpl @Inject constructor(private val context: Context)  :
    DataStoreRepository {
    override suspend fun setLoggedIn(key : String, loggedIn: Boolean) {
        context.dataStore.edit { preferences->
            preferences[booleanPreferencesKey(key)] = loggedIn
        }
    }

    override suspend fun getLoggedIn(key: String): Boolean? {
        val preferencesKey = booleanPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey]
    }

    override suspend fun clearData(key: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences-> preferences.remove(preferencesKey) }
    }

}