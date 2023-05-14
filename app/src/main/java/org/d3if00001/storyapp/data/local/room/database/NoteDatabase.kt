package org.d3if00001.storyapp.data.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if00001.storyapp.data.local.room.dao.UserDao
import org.d3if00001.storyapp.data.local.room.entity.Notes
import org.d3if00001.storyapp.data.local.room.entity.User

@Database(entities = [User::class,Notes::class], version = 3)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
    companion object{
        @Volatile
        private var instance: NoteDatabase? = null

        fun getInstance(context: Context):NoteDatabase{
            return instance?: synchronized(this){
                instance?: buildDatabase(context).also { noteData -> instance = noteData }
            }
        }
        private fun buildDatabase(context: Context): NoteDatabase {
            return Room.databaseBuilder(context, NoteDatabase::class.java, "note_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}