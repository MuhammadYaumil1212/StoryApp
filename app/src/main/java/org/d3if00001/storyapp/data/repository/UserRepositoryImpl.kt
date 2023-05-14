package org.d3if00001.storyapp.data.repository

import android.util.Log
import org.d3if00001.storyapp.data.local.room.dao.UserDao
import org.d3if00001.storyapp.data.local.room.entity.User

import org.d3if00001.storyapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
): UserRepository {
    override fun registerAccount(user: User) {
        userDao.register(user)
    }

    override fun deleteAccount(user:User) {
       try {
           userDao.deleteAccount(user)
       }catch (error:Exception){
           Log.e("Error Delete", "${error.message}")
       }
    }

    override fun verifyLogin(email: String, password: String):User?{
        return userDao.loginAuthentication(email, password)
    }

}