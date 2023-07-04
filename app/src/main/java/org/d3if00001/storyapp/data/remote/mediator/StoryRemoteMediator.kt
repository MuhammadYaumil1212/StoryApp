package org.d3if00001.storyapp.data.remote.mediator

import android.provider.MediaStore.Audio.Media
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import org.d3if00001.storyapp.data.database.Item.StoryResponseItem
import org.d3if00001.storyapp.data.database.StoryDatabase
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import org.d3if00001.storyapp.presentations.viewmodels.AddStoryViewModel
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator @Inject constructor(
    private val database:StoryDatabase,
    private val apiService: APIService,
    private val dataStoreRepository: DataStoreRepository
):RemoteMediator<Int,StoryResponseItem>() {
    private companion object{
        const val INITIAL_PAGE_INDEX=1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoryResponseItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val responseData = apiService.getRemoteStories(
                page = page,
                Bearer = "Bearer ${dataStoreRepository.getToken(AddStoryViewModel.TOKEN_KEY).toString()}",
                location = 0
                )
                val endOfPaginationReached = responseData.listStory.isEmpty()

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.remoteKeysDao().deleteRemoteKeys()
                        database.storyDao().deleteAll()
                    }
                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = responseData.listStory.map {
                        RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    database.remoteKeysDao().insertAll(keys)
                    database.storyDao().insertStory(responseData.listStory)
                }
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (e: Exception) {
                return MediatorResult.Error(e)
            }
            
        }
        private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, StoryResponseItem>): RemoteKeys? {
            return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
                database.remoteKeysDao().getRemoteKeysId(data.id)
            }
        }
        private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, StoryResponseItem>): RemoteKeys? {
            return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
                database.remoteKeysDao().getRemoteKeysId(data.id)
            }
        }
        private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, StoryResponseItem>): RemoteKeys? {
            return state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { id ->
                    database.remoteKeysDao().getRemoteKeysId(id)
                }
            }
        }
    }