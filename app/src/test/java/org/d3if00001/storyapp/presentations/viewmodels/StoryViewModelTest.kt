package org.d3if00001.storyapp.presentations.viewmodels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.d3if00001.storyapp.data.local.preferences.implementations.DataStoreRepositoryImpl
import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import org.d3if00001.storyapp.domain.repository.StoryRepository
import org.d3if00001.storyapp.presentations.utils.StoryAdapter
import org.d3if00001.storyapp.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyViewModel: StoryViewModel
    private val dummyStory = DataDummy.generateDataStory()


    /**
     *
     * Setup before testing
     *
     * **/
    @Before
    fun setUp(){
        storyViewModel = StoryViewModel(
            dataStoreRepository = dataStoreRepository,
            storyRepository = storyRepository
        )
    }
    /**
     * Case :
     * 1. Ketika berhasil memuat data cerita.
     * 2. Memastikan data tidak null.
     * 3. Memastikan jumlah data sesuai dengan yang diharapkan.
     * 4. Memastikan data pertama yang dikembalikan sesuai.
     * **/

    @Test
    fun `when get story should not null and return success`() = runTest{
       val items: Flow<PagingData<getStoryResult>> = storyViewModel.story()
//        val itemsSnapshot : List<getStoryResult> = items
    }
}