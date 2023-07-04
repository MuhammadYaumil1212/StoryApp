package org.d3if00001.storyapp.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if00001.storyapp.data.database.Dao.StoryDao
import org.d3if00001.storyapp.data.database.Item.StoryResponseItem
import org.d3if00001.storyapp.data.remote.mediator.RemoteKeys
import org.d3if00001.storyapp.data.remote.mediator.RemoteKeysDao

@Database(
    entities = [StoryResponseItem::class,RemoteKeys::class],
    version = 2,
    exportSchema = false,
)
abstract class StoryDatabase:RoomDatabase() {
    abstract fun storyDao():StoryDao
    abstract fun remoteKeysDao():RemoteKeysDao
    companion object{
        @Volatile
        private var INSTANCE:StoryDatabase?=null

        @JvmStatic
        fun getDatabase(context: Context):StoryDatabase{
            if(INSTANCE == null){
                synchronized(StoryDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StoryDatabase::class.java,"story_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as StoryDatabase
        }
    }
}