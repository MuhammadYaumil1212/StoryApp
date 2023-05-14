package org.d3if00001.storyapp.domain.repository

import org.d3if00001.storyapp.data.local.room.entity.User


interface UserRepository {
    fun registerAccount(user: User)
    fun deleteAccount(user:User)
    fun verifyLogin(email:String,password:String):User?
}