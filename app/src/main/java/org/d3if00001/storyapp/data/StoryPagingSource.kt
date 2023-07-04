package org.d3if00001.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import org.d3if00001.storyapp.presentations.viewmodels.AddStoryViewModel
import retrofit2.HttpException
import java.io.IOException

class StoryPagingSource(
        private val apiService: APIService,
        private val dataStore:DataStoreRepository
    ):
    PagingSource<Int, getStoryResult>() {
    override fun getRefreshKey(state: PagingState<Int,getStoryResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,getStoryResult> {
        return try {
            val position = params.key ?: 1
            val responseData = apiService.getAllStories(
                Bearer = "Bearer ${dataStore.getToken(AddStoryViewModel.TOKEN_KEY).toString()}",
                location = 0,
                page = params.loadSize
            )
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (responseData.listStory.isEmpty()) null else position+1,
            )
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

}