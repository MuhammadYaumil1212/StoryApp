package org.d3if00001.storyapp.data.local.preferences

import android.content.Context
import org.d3if00001.storyapp.data.repository.UserPreferenceRepository
import org.d3if00001.storyapp.domain.models.User

class UserPreferencesImpl(context:Context) : UserPreferenceRepository {
    companion object{
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL ="email"
        private const val PASSWORD = "password"
        private const val TOKEN = "token"
    }
    private val preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    override fun setUser(value:User,token:String) {
        editor.putString(NAME,value.name)
        editor.putString(EMAIL,value.email)
        editor.putString(PASSWORD,value.password)
        editor.putString(TOKEN,token)
        editor.putBoolean("isLoggedIn",true)
        editor.apply()
    }

    override fun getUser(): User {
        val modelUser = User()
        modelUser.name = preferences.getString(NAME,"")
        modelUser.email = preferences.getString(EMAIL,"")
        modelUser.password = preferences.getString(PASSWORD,"")
        return modelUser
    }
    override fun isLoggedIn(): Boolean = preferences.getBoolean("isLoggedIn",false)
    override fun getToken(): String = preferences.getString("token","")?:""
    override fun logout() {
        editor.remove(TOKEN)
        editor.putBoolean("isLoggedIn",false)
        editor.apply()
    }

}