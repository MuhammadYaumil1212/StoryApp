package org.d3if00001.storyapp.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.response.GetAllStoriesResponse

//class StoryPagingSource(private val ApiService: APIService):
//    PagingSource<Int, GetAllStoriesResponse>() {
//    override fun getRefreshKey(state: PagingState<Int, GetAllStoriesResponse>): Int? {
//
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetAllStoriesResponse> {
//
//    }
//
//}