package org.d3if00001.storyapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.d3if00001.storyapp.data.remote.retrofit.response.GetAllStoriesResponse
import org.d3if00001.storyapp.data.remote.retrofit.response.GetDetailResponse
import org.d3if00001.storyapp.data.remote.retrofit.result.DetailResult
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult

interface StoryRepository {
    fun getStory():Flow<PagingData<getStoryResult>>
    suspend fun detailStory(id:String):GetDetailResponse
    suspend fun mapStory():GetAllStoriesResponse
}