package com.amare.notez.feature.application

import android.app.Application
import androidx.preference.PreferenceManager
import com.amare.notez.util.Utils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NotezApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initTheme()
    }

    private fun initTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        Utils.applyTheme(preferences.getString("preference_key_theme", "") ?: "")
    }
}