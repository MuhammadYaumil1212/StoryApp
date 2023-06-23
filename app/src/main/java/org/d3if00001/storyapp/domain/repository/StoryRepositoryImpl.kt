package org.d3if00001.storyapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import kotlinx.coroutines.flow.Flow
import org.d3if00001.storyapp.data.StoryPagingSource
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val dataStoreRepository: DataStoreRepository
):StoryRepository{
    override fun getStory(): Flow<PagingData<getStoryResult>> {
        val pagingSource = StoryPagingSource(
            dataStore = dataStoreRepository,
            apiService = apiService
        )
        return Pager(
            config = PagingConfig(pageSize = 5),
        ){ pagingSource }.flow
    }
}