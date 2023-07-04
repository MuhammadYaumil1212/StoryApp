package org.d3if00001.storyapp.data.database.Dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.d3if00001.storyapp.data.database.Item.StoryResponseItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story:List<StoryResponseItem>)

    @Query("SELECT * FROM story")
    fun getAllStory():PagingSource<Int, StoryResponseItem>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}