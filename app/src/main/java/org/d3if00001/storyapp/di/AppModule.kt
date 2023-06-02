package org.d3if00001.storyapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.d3if00001.storyapp.data.local.preferences.implementations.DataStoreRepositoryImpl
import org.d3if00001.storyapp.domain.repository.DataStoreRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app:Context
    ): DataStoreRepository = DataStoreRepositoryImpl(app)
}