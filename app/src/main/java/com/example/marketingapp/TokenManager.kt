package com.example.marketingapp

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES_NAME = "my_app_preferences"
        private const val TOKEN_KEY = "auth_token"
    }

    // Save the token to SharedPreferences
    fun saveToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(TOKEN_KEY, token)
            apply()
        }
    }

    // Retrieve the token from SharedPreferences
    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    // Check if a token exists
    fun isTokenAvailable(): Boolean {
        return getToken() != null
    }
    // Clear the token from SharedPreferences
    fun clearToken() {
        with(sharedPreferences.edit()) {
            remove(TOKEN_KEY)
            apply()
        }
    }
}
