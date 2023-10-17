package com.example.challenge2_binar

import android.content.Context
import androidx.core.content.edit

class SharedPreference(context: Context) {
    private val sharedPreference = "userPrefer"
    private var preferences = context.getSharedPreferences(sharedPreference, 0)

    fun setPreferences(isGrid: Boolean) {
        preferences.edit().putBoolean("IS_GRID", isGrid).apply()
    }

    fun getPreferences(): Boolean {
        return preferences.getBoolean("IS_GRID", true)
    }

}
