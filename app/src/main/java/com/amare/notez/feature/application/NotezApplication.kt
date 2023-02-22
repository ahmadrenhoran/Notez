package com.amare.notez.feature.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.amare.notez.util.Constants
import com.amare.notez.util.Utils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NotezApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initTheme()
    }

    private fun initTheme() {
        val preferences = getSharedPreferences(Constants.CURRENT_THEME, Context.MODE_PRIVATE)
        preferences.getString(Constants.CURRENT_THEME, Constants.FOLLOW_SYSTEM_MODE)
            ?.let { Utils.applyTheme(it, this) }
    }
}