package org.d3if00001.storyapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.d3if00001.storyapp.data.local.preferences.implementations.DataStoreRepositoryImpl
import org.d3if00001.storyapp.data.remote.retrofit.APIConfig
import org.d3if00001.storyapp.data.remote.retrofit.APIService
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import org.d3if00001.storyapp.domain.repository.StoryRepository
import org.d3if00001.storyapp.domain.repository.StoryRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app:Context
    ): DataStoreRepository = DataStoreRepositoryImpl(app)

    @Singleton
    @Provides
    fun provideApiService():APIService {
        return APIConfig.getApiService()
    }

    @Singleton
    @Provides
    fun provideStoryRepository(@ApplicationContext app:Context):StoryRepository{
        return StoryRepositoryImpl(
            apiService = provideApiService(),
            dataStoreRepository = provideDataStoreRepository(app)
        )
    }
}