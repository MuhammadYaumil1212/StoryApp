package org.d3if00001.storyapp.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import org.d3if00001.storyapp.data.local.room.entity.Notes
import org.d3if00001.storyapp.data.local.room.entity.User

@Dao
interface UserDao {
    @Transaction
    @Insert
    fun register(user:User)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(note:Notes)

    @Transaction
    @Query("SELECT * FROM users WHERE email LIKE :email AND password LIKE :password")
    fun loginAuthentication(email: String,password:String):User?

    @Transaction
    @Query("" +
            "SELECT imageStory,titleNotes,description,bodyNotes,date " +
            "FROM Notes " +
            "WHERE userId = :userId " +
            "ORDER BY id ASC"
    )
    fun showListNotesByUserId(userId:Int): LiveData<List<Notes>>

    @Transaction
    @Query("SELECT imageStory,titleNotes,description,bodyNotes,date FROM Notes WHERE id = :id")
    fun showDetailNoteById(id:Int):Notes

    @Transaction
    @Delete
    fun deleteAccount(user:User)
}