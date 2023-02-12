package com.amare.notez.feature.application

import android.app.Application
import androidx.preference.PreferenceManager
import com.amare.notez.util.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NotezApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initTheme()
    }

    private fun initTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        ThemeManager.applyTheme(preferences.getString("preference_key_theme", "") ?: "")
    }
}