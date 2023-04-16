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
    }
    private val preferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    override fun setUser(token:String) {
        val editor = preferences.edit()
        editor.putString(NAME,token.name)
        editor.putString(EMAIL,value.email)
        editor.putString(PASSWORD,value.password)
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

}