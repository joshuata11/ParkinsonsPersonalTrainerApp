package com.example.ppt

import android.content.Context
import android.content.SharedPreferences

object PrefObject {
    private const val NAME = "account_info"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var sharedPref: SharedPreferences

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences(NAME, MODE)
    }

    fun getUsername(): String? {
        return sharedPref.getString("userKey", "")
    }

    fun setUsername(username: String) {
        val editor = sharedPref.edit()
        editor.putString("userKey",username)
        editor.apply()
    }

    fun getPassword(): String? {
        return sharedPref.getString("passKey", "")
    }

    fun setPassword(password: String) {
        val editor = sharedPref.edit()
        editor.putString("passKey",password)
        editor.apply()
    }

    fun getRemember(): Boolean {
        return sharedPref.getBoolean("rememberKey", false)
    }

    fun setRemember(remember: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean("rememberKey",remember)
        editor.apply()
    }

    fun getSetup(): Boolean {
        return sharedPref.getBoolean("setupKey", true)
    }

    fun setSetup(setup: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean("setupKey",setup)
        editor.apply()
    }

    fun getSession(): Boolean {
        return sharedPref.getBoolean("sessionKey", false)
    }

    fun setSession(session: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean("sessionKey",session)
        editor.apply()
    }

    fun getActivity(): String? {
        return sharedPref.getString("activityKey", "Resting")
    }

    fun setActivity(activity: String) {
        val editor = sharedPref.edit()
        editor.putString("activityKey",activity)
        editor.apply()
    }
}