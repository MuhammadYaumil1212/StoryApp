package org.d3if00001.storyapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.d3if00001.storyapp.domain.models.repository.DataStoreRepository
import org.d3if00001.storyapp.data.local.preferences.implementations.DataStoreRepositoryImpl
import org.d3if00001.storyapp.data.local.room.dao.UserDao
import org.d3if00001.storyapp.data.local.room.database.NoteDatabase
import org.d3if00001.storyapp.data.repository.UserRepositoryImpl
import org.d3if00001.storyapp.domain.models.repository.UserRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app:Context
    ): DataStoreRepository = DataStoreRepositoryImpl(app)

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao) : UserRepository {
        return UserRepositoryImpl(userDao)
    }
    @Provides
    @Singleton
    fun provideUserDao(database:NoteDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext app:Context):NoteDatabase{
        return NoteDatabase.getInstance(app)
    }

    @Provides
    @Singleton
    fun provideUserRepositoryImpl(userDao: UserDao): UserRepositoryImpl {
        return UserRepositoryImpl(userDao)
    }
}