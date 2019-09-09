package com.nikola.kotlinarchitecturecomponents.utility

import android.content.Context

class PreferenceUtils(context: Context?) {

    private val PREFERENCE_NAME = "SharedPreference"
    var KEY_USERNAME = "username"
    var KEY_PASSWORD = "password"
    var KEY_LOGGED = "logged"

    private val preference = context?.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setRememberedUsername(rememberedUsername:String) {
        val editor = preference?.edit()
        editor?.putString(KEY_USERNAME, rememberedUsername)
        editor?.apply()
    }

    fun getRememberedUsername(): String? = preference?.getString(KEY_USERNAME, null)

    fun setRememberedPassword(rememberedPassword:String) {
        val editor = preference?.edit()
        editor?.putString(KEY_PASSWORD, rememberedPassword)
        editor?.apply()
    }

    fun getRememberedPassword(): String? = preference?.getString(KEY_PASSWORD, null)

    fun setLogged (key:Int) {
        val editor = preference?.edit()
        editor?.putInt(KEY_LOGGED, key)
        editor?.apply()
    }

    fun getLoggedStatus(): Int? = preference?.getInt(KEY_LOGGED, 0)

}